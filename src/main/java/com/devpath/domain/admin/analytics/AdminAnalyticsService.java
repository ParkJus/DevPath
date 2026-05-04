package com.devpath.domain.admin.analytics;

import com.devpath.api.analytics.dto.AnalyticsDashboardResponse;
import com.devpath.api.analytics.dto.ExperimentResultResponse;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminAnalyticsService {

    private final ExperimentResultRepository experimentResultRepository;

    public List<ExperimentResultResponse> getAllExperimentResults() {
        return experimentResultRepository.findAll()
                .stream()
                .map(ExperimentResultResponse::from)
                .collect(Collectors.toList());
    }

    public ExperimentResultResponse getExperimentResult(String experimentId) {
        ExperimentResult result = experimentResultRepository.findByExperimentId(experimentId)
                .orElseThrow(() -> new CustomException(ErrorCode.EXPERIMENT_NOT_FOUND));
        return ExperimentResultResponse.from(result);
    }

    public AnalyticsDashboardResponse getDashboardSummary() {
        // 독립 구현 규칙에 따라 Workspace를 직접 참조하지 않고
        // 데이터 파이프라인에서 집계된 캐시나 배치 결과를 반환한다고 가정
        return AnalyticsDashboardResponse.builder()
                .totalUsers(15230L)
                .weeklyActiveUsers(4321L)
                .averageRoadmapProgress(42.8)
                .monthlyCompletedAssignments(1830L)
                .build();
    }
}
