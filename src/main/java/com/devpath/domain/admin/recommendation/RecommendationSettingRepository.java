package com.devpath.domain.admin.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendationSettingRepository extends JpaRepository<RecommendationSetting, Long> {

    Optional<RecommendationSetting> findBySettingKey(String settingKey);
}
