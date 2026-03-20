package com.devpath.domain.learning.entity.recommendation;

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
@Table(name = "recommendation_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    // 이력의 대상 학습자와의 연관관계다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 변경 이전 추천 상태를 문자열로 저장한다.
    @Column(name = "before_status", length = 30)
    private String beforeStatus;

    // 변경 이후 추천 상태를 문자열로 저장한다.
    @Column(name = "after_status", length = 30)
    private String afterStatus;

    // 변경이 발생한 노드명 또는 추천 컨텍스트를 저장한다.
    @Column(name = "context", columnDefinition = "TEXT")
    private String context;

    // 이력 생성 시각을 자동 저장한다.
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public RecommendationHistory(User user, String beforeStatus, String afterStatus, String context) {
        this.user = user;
        this.beforeStatus = beforeStatus;
        this.afterStatus = afterStatus;
        this.context = context;
    }
}
