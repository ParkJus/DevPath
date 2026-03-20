package com.devpath.api.evaluation.dto.response;

import com.devpath.domain.learning.entity.SubmissionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "제출물 피드백 저장 응답 DTO")
public class FeedbackResponse {

    // 제출 ID다.
    @Schema(description = "제출 ID", example = "1")
    private Long submissionId;

    // 피드백 유형이다.
    @Schema(description = "피드백 유형", example = "INDIVIDUAL")
    private String feedbackType;

    // 저장된 피드백 내용이다.
    @Schema(description = "피드백 내용", example = "테스트 코드 커버리지를 조금 더 보강하면 좋습니다.")
    private String content;

    // 피드백 저장 후 제출 상태다.
    @Schema(description = "제출 상태", example = "GRADED")
    private SubmissionStatus submissionStatus;

    // 피드백 저장 시 기준이 된 점수다.
    @Schema(description = "현재 점수", example = "24")
    private Integer totalScore;

    // 마지막 채점/피드백 반영 시각이다.
    @Schema(description = "반영 시각", example = "2026-03-20T14:00:00")
    private LocalDateTime updatedAt;

    @Builder
    public FeedbackResponse(
            Long submissionId,
            String feedbackType,
            String content,
            SubmissionStatus submissionStatus,
            Integer totalScore,
            LocalDateTime updatedAt
    ) {
        this.submissionId = submissionId;
        this.feedbackType = feedbackType;
        this.content = content;
        this.submissionStatus = submissionStatus;
        this.totalScore = totalScore;
        this.updatedAt = updatedAt;
    }
}
