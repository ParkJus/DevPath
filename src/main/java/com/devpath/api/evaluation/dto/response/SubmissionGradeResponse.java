package com.devpath.api.evaluation.dto.response;

import com.devpath.domain.learning.entity.SubmissionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "제출물 채점 결과 응답 DTO")
public class SubmissionGradeResponse {

    // 제출 ID다.
    @Schema(description = "제출 ID", example = "1")
    private Long submissionId;

    // 채점자 ID다.
    @Schema(description = "채점자 ID", example = "3")
    private Long graderId;

    // 최종 채점 점수다.
    @Schema(description = "최종 점수", example = "24")
    private Integer totalScore;

    // 채점 이후 제출 상태다.
    @Schema(description = "제출 상태", example = "GRADED")
    private SubmissionStatus submissionStatus;

    // 채점 완료 시각이다.
    @Schema(description = "채점 완료 시각", example = "2026-03-20T13:30:00")
    private LocalDateTime gradedAt;

    // 루브릭별 채점 결과 목록이다.
    @Schema(description = "루브릭별 채점 결과 목록")
    private List<RubricGradeItem> rubricGrades = new ArrayList<>();

    @Builder
    public SubmissionGradeResponse(
            Long submissionId,
            Long graderId,
            Integer totalScore,
            SubmissionStatus submissionStatus,
            LocalDateTime gradedAt,
            List<RubricGradeItem> rubricGrades
    ) {
        this.submissionId = submissionId;
        this.graderId = graderId;
        this.totalScore = totalScore;
        this.submissionStatus = submissionStatus;
        this.gradedAt = gradedAt;
        this.rubricGrades = rubricGrades == null ? new ArrayList<>() : rubricGrades;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "개별 루브릭 채점 결과 DTO")
    public static class RubricGradeItem {

        // 루브릭 ID다.
        @Schema(description = "루브릭 ID", example = "1")
        private Long rubricId;

        // 루브릭 기준명이다.
        @Schema(description = "루브릭 기준명", example = "기능 구현 완성도")
        private String criteriaName;

        // 최대 점수다.
        @Schema(description = "최대 점수", example = "10")
        private Integer maxPoints;

        // 부여 점수다.
        @Schema(description = "부여 점수", example = "8")
        private Integer earnedPoints;

        @Builder
        public RubricGradeItem(Long rubricId, String criteriaName, Integer maxPoints, Integer earnedPoints) {
            this.rubricId = rubricId;
            this.criteriaName = criteriaName;
            this.maxPoints = maxPoints;
            this.earnedPoints = earnedPoints;
        }
    }
}
