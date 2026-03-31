package com.devpath.api.study.controller;

import com.devpath.api.study.dto.StudyMatchRecommendationResponse;
import com.devpath.api.study.dto.StudyMatchResponse;
import com.devpath.api.study.service.StudyMatchService;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/study-matches")
@RequiredArgsConstructor
@Tag(name = "Learner - Study Match", description = "학습자 자동 매칭 및 추천 API")
public class StudyMatchController {

    private final StudyMatchService studyMatchService;

    @GetMapping
    @Operation(summary = "내 매칭 내역 조회", description = "JWT 인증 사용자 기준으로 내가 요청했거나 받은 스터디 매칭 내역을 조회합니다.")
    public ApiResponse<List<StudyMatchResponse>> getMyMatches(
            @Parameter(hidden = true) @AuthenticationPrincipal Long learnerId) {
        validateAuthenticatedLearnerId(learnerId);
        return ApiResponse.ok(studyMatchService.getMyMatches(learnerId));
    }

    @GetMapping("/recommendations")
    @Operation(summary = "자동 매칭 추천 조회", description = "JWT 인증 사용자 기준으로 같은 노드를 진행 중인 학습자 목록을 추천받습니다.")
    public ApiResponse<List<StudyMatchRecommendationResponse>> getRecommendations(
            @Parameter(hidden = true) @AuthenticationPrincipal Long learnerId) {
        validateAuthenticatedLearnerId(learnerId);
        return ApiResponse.ok(studyMatchService.getRecommendations(learnerId));
    }

    private void validateAuthenticatedLearnerId(Long learnerId) {
        if (learnerId == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }
}
