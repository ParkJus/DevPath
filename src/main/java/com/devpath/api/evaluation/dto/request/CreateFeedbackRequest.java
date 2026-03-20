package com.devpath.api.evaluation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "제출물 피드백 생성 요청 DTO")
public class CreateFeedbackRequest {

    // INDIVIDUAL 또는 COMMON 중 하나를 전달하며 개인 피드백인지 공통 피드백인지 구분한다.
    @NotBlank
    @Schema(description = "피드백 유형", example = "INDIVIDUAL")
    private String feedbackType;

    // 저장할 피드백 본문이다.
    @NotBlank
    @Schema(description = "피드백 내용", example = "테스트 코드 커버리지를 조금 더 보강하면 좋습니다.")
    private String content;

    @Builder
    public CreateFeedbackRequest(String feedbackType, String content) {
        this.feedbackType = feedbackType;
        this.content = content;
    }
}
