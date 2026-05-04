package com.devpath.domain.admin.analytics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperimentResultRepository extends JpaRepository<ExperimentResult, Long> {

    Optional<ExperimentResult> findByExperimentId(String experimentId);
}
