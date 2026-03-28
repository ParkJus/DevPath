package com.devpath.api.admin.service;

import com.devpath.api.admin.dto.governance.NodeCompletionRuleRequest;
import com.devpath.api.admin.dto.governance.NodePrerequisitesRequest;
import com.devpath.api.admin.dto.governance.NodeRequiredTagsRequest;
import com.devpath.api.admin.dto.governance.NodeTypeRequest;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.roadmap.entity.NodeCompletionRule;
import com.devpath.domain.roadmap.entity.NodeRequiredTag;
import com.devpath.domain.roadmap.entity.Prerequisite;
import com.devpath.domain.roadmap.entity.RoadmapNode;
import com.devpath.domain.roadmap.repository.NodeCompletionRuleRepository;
import com.devpath.domain.roadmap.repository.NodeRequiredTagRepository;
import com.devpath.domain.roadmap.repository.PrerequisiteRepository;
import com.devpath.domain.roadmap.repository.RoadmapNodeRepository;
import com.devpath.domain.user.entity.Tag;
import com.devpath.domain.user.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminNodeGovernanceService {

    private static final Set<String> ALLOWED_NODE_TYPES =
            Set.of("CONCEPT", "PRACTICE", "PROJECT", "REVIEW", "EXAM");

    private final RoadmapNodeRepository roadmapNodeRepository;
    private final TagRepository tagRepository;
    private final NodeRequiredTagRepository nodeRequiredTagRepository;
    private final PrerequisiteRepository prerequisiteRepository;
    private final NodeCompletionRuleRepository nodeCompletionRuleRepository;

    public void updateRequiredTags(Long nodeId, NodeRequiredTagsRequest request) {
        RoadmapNode node = getNode(nodeId);
        List<String> tagNames = request != null && request.getRequiredTags() != null
                ? request.getRequiredTags() : List.of();

        List<Tag> tags = tagNames.stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseThrow(() -> new CustomException(ErrorCode.TAG_NOT_FOUND)))
                .collect(java.util.stream.Collectors.toList());

        nodeRequiredTagRepository.deleteAllByNodeId(nodeId);

        if (tags.isEmpty()) {
            return;
        }

        List<NodeRequiredTag> mappings =
                tags.stream().map(tag -> NodeRequiredTag.builder().node(node).tag(tag).build()).toList();
        nodeRequiredTagRepository.saveAll(mappings);
    }

    public void updateNodeType(Long nodeId, NodeTypeRequest request) {
        RoadmapNode node = getNode(nodeId);
        String nodeType = normalizeRequiredValue(request == null ? null : request.getNodeType());

        if (!ALLOWED_NODE_TYPES.contains(nodeType)) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        node.changeNodeType(nodeType);
    }

    public void updatePrerequisites(Long nodeId, NodePrerequisitesRequest request) {
        RoadmapNode node = getNode(nodeId);
        List<Long> prerequisiteNodeIds =
                normalizeUniqueIds(request == null ? null : request.getPrerequisiteNodeIds());

        if (prerequisiteNodeIds.contains(nodeId)) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        List<RoadmapNode> prerequisiteNodes = loadNodes(prerequisiteNodeIds);
        validateSameRoadmap(node, prerequisiteNodes);

        prerequisiteRepository.deleteAllByNode(node);

        if (prerequisiteNodes.isEmpty()) {
            return;
        }

        List<Prerequisite> prerequisites =
                prerequisiteNodes.stream()
                        .map(prerequisiteNode -> Prerequisite.builder().node(node).preNode(prerequisiteNode).build())
                        .toList();
        prerequisiteRepository.saveAll(prerequisites);
    }

    public void updateCompletionRule(Long nodeId, NodeCompletionRuleRequest request) {
        RoadmapNode node = getNode(nodeId);
        String criteriaType = normalizeRequiredValue(
                request == null ? null : request.getCompletionRuleDescription());
        String criteriaValue = request != null && request.getRequiredProgressRate() != null
                ? request.getRequiredProgressRate().toString() : "0";

        NodeCompletionRule rule =
                nodeCompletionRuleRepository
                        .findByNodeNodeId(nodeId)
                        .orElseGet(() ->
                                nodeCompletionRuleRepository.save(
                                        NodeCompletionRule.builder()
                                                .node(node)
                                                .criteriaType(criteriaType)
                                                .criteriaValue(criteriaValue)
                                                .build()));

        rule.updateRule(criteriaType, criteriaValue);
    }

    private RoadmapNode getNode(Long nodeId) {
        return roadmapNodeRepository
                .findById(nodeId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROADMAP_NODE_NOT_FOUND));
    }

    private List<RoadmapNode> loadNodes(List<Long> nodeIds) {
        if (nodeIds.isEmpty()) {
            return List.of();
        }

        List<RoadmapNode> nodes = roadmapNodeRepository.findAllById(nodeIds);

        if (nodes.size() != nodeIds.size()) {
            throw new CustomException(ErrorCode.ROADMAP_NODE_NOT_FOUND);
        }

        Map<Long, RoadmapNode> nodesById = new LinkedHashMap<>();
        for (RoadmapNode node : nodes) {
            nodesById.put(node.getNodeId(), node);
        }

        return nodeIds.stream().map(nodesById::get).toList();
    }

    private void validateSameRoadmap(RoadmapNode node, List<RoadmapNode> prerequisiteNodes) {
        Long roadmapId = node.getRoadmap().getRoadmapId();

        for (RoadmapNode prerequisiteNode : prerequisiteNodes) {
            if (!roadmapId.equals(prerequisiteNode.getRoadmap().getRoadmapId())) {
                throw new CustomException(ErrorCode.INVALID_INPUT);
            }
        }
    }

    private List<Long> normalizeUniqueIds(List<Long> values) {
        if (values == null || values.isEmpty()) {
            return List.of();
        }

        LinkedHashSet<Long> uniqueIds = new LinkedHashSet<>();

        for (Long value : values) {
            if (value == null || !uniqueIds.add(value)) {
                throw new CustomException(ErrorCode.INVALID_INPUT);
            }
        }

        return uniqueIds.stream().toList();
    }

    private String normalizeRequiredValue(String value) {
        if (value == null || value.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        return value.trim().toUpperCase();
    }
}