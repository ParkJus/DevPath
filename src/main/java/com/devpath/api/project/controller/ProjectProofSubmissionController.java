package com.devpath.api.project.controller;

import com.devpath.api.project.dto.ProjectAdvancedRequests.ProofSubmissionRequest;
import com.devpath.api.project.dto.ProjectAdvancedResponses.ProofSubmissionResponse;
import com.devpath.api.project.service.ProjectProofSubmissionService;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/proof-submissions")
@RequiredArgsConstructor
@Tag(name = "Project - Proof Submission", description = "Proof Card 기반 이력 제출 API")
public class ProjectProofSubmissionController {

    private final ProjectProofSubmissionService projectProofSubmissionService;

    @PostMapping
    @Operation(summary = "증명서 제출", description = "발급받은 Proof Card를 프로젝트 이력으로 제출합니다.")
    public ApiResponse<ProofSubmissionResponse> submitProof(
            @Valid @RequestBody ProofSubmissionRequest request,
            @RequestParam(defaultValue = "1") Long submitterId
    ) {
        return ApiResponse.ok(projectProofSubmissionService.submitProof(request, submitterId));
    }

    @GetMapping
    @Operation(summary = "제출 내역 목록 조회", description = "특정 프로젝트에 제출된 Proof Card 이력을 조회합니다.")
    public ApiResponse<List<ProofSubmissionResponse>> getSubmissions(@RequestParam Long projectId) {
        return ApiResponse.ok(projectProofSubmissionService.getSubmissions(projectId));
    }

    @GetMapping("/{submissionId}")
    @Operation(summary = "제출 내역 상세 조회", description = "특정 제출 건의 상세 정보를 조회합니다.")
    public ApiResponse<ProofSubmissionResponse> getSubmissionDetail(@PathVariable Long submissionId) {
        return ApiResponse.ok(projectProofSubmissionService.getSubmissionDetail(submissionId));
    }
}
