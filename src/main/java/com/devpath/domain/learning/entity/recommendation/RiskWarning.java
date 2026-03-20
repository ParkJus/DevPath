package com.devpath.domain.learning.entity.recommendation;

import com.devpath.domain.roadmap.entity.RoadmapNode;
import com.devpath.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "risk_warnings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiskWarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warning_id")
    private Long id;

    // 경고 대상 학습자와의 연관관계다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 난이도/리스크 경고가 발생한 노드와의 연관관계다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id")
    private RoadmapNode roadmapNode;

    // 경고 유형을 저장한다. (예: DIFFICULTY_TOO_HIGH, PREREQUISITE_MISSING 등)
    @Column(name = "warning_type", nullable = false, length = 50)
    private String warningType;

    // 경고 상세 메시지를 저장한다.
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    // 학습자가 경고를 확인했는지 여부를 나타낸다.
    @Column(name = "is_acknowledged", nullable = false)
    private Boolean isAcknowledged = false;

    // 생성 시각을 자동 저장한다.
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public RiskWarning(User user, RoadmapNode roadmapNode, String warningType, String message) {
        this.user = user;
        this.roadmapNode = roadmapNode;
        this.warningType = warningType;
        this.message = message;
        this.isAcknowledged = false;
    }

    // 학습자가 경고를 확인 처리한다.
    public void acknowledge() {
        this.isAcknowledged = true;
    }
}
