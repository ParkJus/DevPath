package com.devpath.domain.learning.entity.recommendation;

import com.devpath.domain.roadmap.entity.RoadmapNode;
import com.devpath.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "supplement_recommendations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupplementRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long id;

    // 추천 대상 학습자와의 연관관계다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 보강 대상으로 추천된 로드맵 노드와의 연관관계다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private RoadmapNode roadmapNode;

    // AI가 생성한 추천 사유를 저장한다.
    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    // 학습자가 추천을 수락/거절했는지 여부를 나타낸다.
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private RecommendationStatus status = RecommendationStatus.PENDING;

    // 생성 시각을 자동 저장한다.
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public SupplementRecommendation(User user, RoadmapNode roadmapNode, String reason) {
        this.user = user;
        this.roadmapNode = roadmapNode;
        this.reason = reason;
        this.status = RecommendationStatus.PENDING;
    }

    // 학습자가 추천을 수락 처리한다.
    public void approve() {
        this.status = RecommendationStatus.APPROVED;
    }

    // 학습자가 추천을 거절 처리한다.
    public void reject() {
        this.status = RecommendationStatus.REJECTED;
    }
}
