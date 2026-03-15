package com.devpath.api.admin.service;

import com.devpath.api.admin.dto.NodeGovernanceRequests.*;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.roadmap.entity.RoadmapNode;
import com.devpath.domain.roadmap.repository.RoadmapNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminNodeGovernanceService {

    private final RoadmapNodeRepository roadmapNodeRepository;

    // 1. 노드 필수 태그 설정
    public void updateRequiredTags(Long nodeId, UpdateRequiredTags request) {
        RoadmapNode node = roadmapNodeRepository.findById(nodeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        // TODO: NodeRequiredTagRepository를 통해 기존 태그 삭제 후 새 태그 매핑 로직 추가
    }

    // 2. 노드 타입 설정
    public void updateNodeType(Long nodeId, UpdateNodeType request) {
        RoadmapNode node = roadmapNodeRepository.findById(nodeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        // TODO: RoadmapNode 엔티티에 비즈니스 메서드(changeNodeType 등)를 추가하여 상태 업데이트
    }

    // 3. 선행 조건 설정
    public void updatePrerequisites(Long nodeId, UpdatePrerequisites request) {
        RoadmapNode node = roadmapNodeRepository.findById(nodeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        // TODO: PrerequisiteRepository를 통해 기존 선행조건 삭제 후 새 선행조건 매핑
    }

    // 4. 완료 기준 설정
    public void updateCompletionRule(Long nodeId, UpdateCompletionRule request) {
        RoadmapNode node = roadmapNodeRepository.findById(nodeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        // TODO: NodeCompletionCriteriaRepository 등을 통해 완료 기준 갱신
    }
}