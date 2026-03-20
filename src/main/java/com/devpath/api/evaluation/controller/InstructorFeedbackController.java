package com.devpath.api.evaluation.controller;

import com.devpath.api.evaluation.dto.request.CreateFeedbackRequest;
import com.devpath.api.evaluation.dto.response.FeedbackResponse;
import com.devpath.api.evaluation.service.FeedbackService;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Instructor - Feedback", description = "강사용 제출물 피드백 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/evaluation/instructor/submissions")
public class InstructorFeedbackController {

    private final FeedbackService feedbackService;

    @Operation(summary = "개별/공통 피드백 작성", description = "채점 완료된 제출물에 대해 INDIVIDUAL 또는 COMMON 피드백을 저장합니다.")
    @PostMapping("/{submissionId}/feedback")
    public ResponseEntity<ApiResponse<FeedbackResponse>> createFeedback(
            @Parameter(description = "강사 ID", example = "3")
            @RequestParam Long userId,
            @Parameter(description = "제출 ID", example = "1")
            @PathVariable Long submissionId,
            @Valid @RequestBody CreateFeedbackRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "피드백이 저장되었습니다.",
                feedbackService.createFeedback(userId, submissionId, request)
        ));
    }
}
