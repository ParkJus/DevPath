package com.devpath.domain.learning.repository.recommendation;

import com.devpath.domain.learning.entity.recommendation.RecommendationHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationHistoryRepository extends JpaRepository<RecommendationHistory, Long> {

    // 특정 학습자의 추천 변경 이력을 최신순으로 조회한다.
    List<RecommendationHistory> findByUserIdOrderByCreatedAtDesc(Long userId);
}
