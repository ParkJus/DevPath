package com.devpath.api.admin.controller;

import com.devpath.api.admin.dto.PolicyGovernanceRequests.*;
import com.devpath.api.admin.service.AdminPolicyAndMappingService;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin - Policy & Mapping Governance", description = "관리자 시스템 정책 및 강의-노드 매핑 API")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminPolicyAndMappingController {

    private final AdminPolicyAndMappingService policyAndMappingService;

    @Operation(summary = "강의-노드 매핑 후보 검토", description = "등록된 태그를 바탕으로 강의가 들어갈 적합한 노드 후보군을 조회합니다.")
    @GetMapping("/course-node-mappings/candidates")
    public ApiResponse<Object> getMappingCandidates() {
        return ApiResponse.ok(policyAndMappingService.getMappingCandidates());
    }

    @Operation(summary = "강의-노드 매핑 확정", description = "관리자가 특정 강의를 로드맵의 특정 노드에 공식적으로 매핑합니다.")
    @PutMapping("/courses/{courseId}/node-mapping")
    public ApiResponse<Void> updateCourseNodeMapping(@PathVariable Long courseId, @RequestBody UpdateNodeMapping request) {
        policyAndMappingService.updateCourseNodeMapping(courseId, request);
        return ApiResponse.ok();
    }

    @Operation(summary = "시스템 정책 조회", description = "현재 플랫폼에 적용된 수수료율 등 시스템 기본 정책을 조회합니다.")
    @GetMapping("/system-policies")
    public ApiResponse<Object> getSystemPolicies() {
        return ApiResponse.ok(policyAndMappingService.getSystemPolicies());
    }

    @Operation(summary = "시스템 정책 수정", description = "플랫폼 기본 수수료율 등 운영 정책을 일괄 변경합니다.")
    @PutMapping("/system-policies")
    public ApiResponse<Void> updateSystemPolicies(@RequestBody UpdateSystemPolicy request) {
        policyAndMappingService.updateSystemPolicies(request);
        return ApiResponse.ok();
    }

    @Operation(summary = "영상 스트리밍 정책 수정", description = "불법 녹화 방지를 위한 HLS 암호화 정책 및 동시접속 제한을 설정합니다.")
    @PutMapping("/streaming-policy")
    public ApiResponse<Void> updateStreamingPolicy(@RequestBody UpdateStreamingPolicy request) {
        policyAndMappingService.updateStreamingPolicy(request);
        return ApiResponse.ok();
    }
}