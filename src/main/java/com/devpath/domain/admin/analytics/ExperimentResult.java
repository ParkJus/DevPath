package com.devpath.domain.admin.analytics;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "experiment_results")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ExperimentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "experiment_id", nullable = false, length = 100)
    private String experimentId;

    @Column(name = "experiment_name", nullable = false)
    private String experimentName;

    @Column(columnDefinition = "JSON", nullable = false)
    private String metricsJson;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public ExperimentResult(String experimentId, String experimentName, String metricsJson) {
        this.experimentId = experimentId;
        this.experimentName = experimentName;
        this.metricsJson = metricsJson;
    }
}
