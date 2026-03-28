package com.devpath.api.admin.controller;

import com.devpath.api.admin.dto.governance.NodeCompletionRuleRequest;
import com.devpath.api.admin.dto.governance.NodePrerequisitesRequest;
import com.devpath.api.admin.dto.governance.NodeRequiredTagsRequest;
import com.devpath.api.admin.dto.governance.NodeTypeRequest;
import com.devpath.api.admin.service.AdminNodeGovernanceService;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin - Node Governance", description = "관리자 노드 거버넌스 API")
@RestController
@RequestMapping("/api/admin/nodes")
@RequiredArgsConstructor
public class AdminNodeGovernanceController {

    private final AdminNodeGovernanceService adminNodeGovernanceService;

    @Operation(summary = "노드 필수 태그 수정")
    @PutMapping("/{nodeId}/required-tags")
    public ApiResponse<Void> updateRequiredTags(
            @PathVariable Long nodeId,
            @RequestBody @Valid NodeRequiredTagsRequest request) {
        adminNodeGovernanceService.updateRequiredTags(nodeId, request);
        return ApiResponse.success("노드 필수 태그가 수정되었습니다.", null);
    }

    @Operation(summary = "노드 유형 수정", description = "개념/실습/제작/리뷰/시험")
    @PutMapping("/{nodeId}/type")
    public ApiResponse<Void> updateNodeType(
            @PathVariable Long nodeId,
            @RequestBody @Valid NodeTypeRequest request) {
        adminNodeGovernanceService.updateNodeType(nodeId, request);
        return ApiResponse.success("노드 유형이 수정되었습니다.", null);
    }

    @Operation(summary = "노드 선수조건 수정")
    @PutMapping("/{nodeId}/prerequisites")
    public ApiResponse<Void> updatePrerequisites(
            @PathVariable Long nodeId,
            @RequestBody @Valid NodePrerequisitesRequest request) {
        adminNodeGovernanceService.updatePrerequisites(nodeId, request);
        return ApiResponse.success("노드 선수조건이 수정되었습니다.", null);
    }

    @Operation(summary = "노드 완료기준 수정")
    @PutMapping("/{nodeId}/completion-rule")
    public ApiResponse<Void> updateCompletionRule(
            @PathVariable Long nodeId,
            @RequestBody @Valid NodeCompletionRuleRequest request) {
        adminNodeGovernanceService.updateCompletionRule(nodeId, request);
        return ApiResponse.success("노드 완료기준이 수정되었습니다.", null);
    }
}