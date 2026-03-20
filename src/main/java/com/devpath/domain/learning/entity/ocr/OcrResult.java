package com.devpath.domain.learning.entity.ocr;

import com.devpath.domain.course.entity.Lesson;
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
@Table(name = "ocr_results")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OcrResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ocr_id")
    private Long id;

    // OCR을 요청한 학습자와의 연관관계다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // OCR이 수행된 강의 레슨과의 연관관계다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    // OCR을 수행한 프레임의 재생 위치를 초 단위로 저장한다.
    // 타임스탬프 매핑 및 해당 구간 이동에 활용된다.
    @Column(name = "frame_timestamp_second", nullable = false)
    private Integer frameTimestampSecond;

    // EasyOCR Flask 서버가 추출한 텍스트 전문을 저장한다.
    @Column(name = "extracted_text", columnDefinition = "TEXT")
    private String extractedText;

    // OCR 인식 신뢰도(0.0~1.0)를 저장한다.
    @Column(name = "confidence")
    private Double confidence;

    // 생성 시각을 자동 저장한다.
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public OcrResult(User user, Lesson lesson, Integer frameTimestampSecond,
            String extractedText, Double confidence) {
        this.user = user;
        this.lesson = lesson;
        this.frameTimestampSecond = frameTimestampSecond;
        this.extractedText = extractedText;
        this.confidence = confidence;
    }
}
