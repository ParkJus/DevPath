package com.devpath.domain.learning.repository.recommendation;

import com.devpath.domain.learning.entity.recommendation.RiskWarning;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskWarningRepository extends JpaRepository<RiskWarning, Long> {

    // 특정 학습자의 미확인 경고 목록을 최신순으로 조회한다.
    List<RiskWarning> findByUserIdAndIsAcknowledgedFalseOrderByCreatedAtDesc(Long userId);

    // 특정 학습자의 전체 경고 목록을 최신순으로 조회한다.
    List<RiskWarning> findByUserIdOrderByCreatedAtDesc(Long userId);
}
