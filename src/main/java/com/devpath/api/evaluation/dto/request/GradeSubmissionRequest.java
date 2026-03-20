package com.devpath.api.evaluation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "제출물 루브릭 기반 채점 요청 DTO")
public class GradeSubmissionRequest {

    // 루브릭별 점수 목록이며 모든 루브릭에 대해 1개씩 전달하는 것을 기준으로 한다.
    @Valid
    @NotEmpty
    @Schema(description = "루브릭별 점수 목록")
    private List<RubricScoreRequest> rubricScores = new ArrayList<>();

    @Builder
    public GradeSubmissionRequest(List<RubricScoreRequest> rubricScores) {
        this.rubricScores = rubricScores == null ? new ArrayList<>() : rubricScores;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "개별 루브릭 점수 요청 DTO")
    public static class RubricScoreRequest {

        // 어떤 루브릭 기준에 대한 점수인지 식별하기 위한 rubricId다.
        @NotNull
        @Schema(description = "루브릭 ID", example = "1")
        private Long rubricId;

        // 해당 루브릭 기준에서 부여한 점수이며 0 이상이어야 한다.
        @NotNull
        @Min(0)
        @Schema(description = "획득 점수", example = "8")
        private Integer earnedPoints;

        @Builder
        public RubricScoreRequest(Long rubricId, Integer earnedPoints) {
            this.rubricId = rubricId;
            this.earnedPoints = earnedPoints;
        }
    }
}
