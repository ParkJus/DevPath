package com.devpath.domain.learning.repository.recommendation;

import com.devpath.domain.learning.entity.recommendation.RecommendationStatus;
import com.devpath.domain.learning.entity.recommendation.SupplementRecommendation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementRecommendationRepository extends JpaRepository<SupplementRecommendation, Long> {

    // 특정 학습자의 특정 상태 추천 목록을 최신순으로 조회한다.
    List<SupplementRecommendation> findByUserIdAndStatusOrderByCreatedAtDesc(
            Long userId, RecommendationStatus status);

    // 특정 학습자의 전체 추천 목록을 최신순으로 조회한다.
    List<SupplementRecommendation> findByUserIdOrderByCreatedAtDesc(Long userId);
}
