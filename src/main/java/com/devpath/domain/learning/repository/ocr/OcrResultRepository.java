package com.devpath.domain.learning.repository.ocr;

import com.devpath.domain.learning.entity.ocr.OcrResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcrResultRepository extends JpaRepository<OcrResult, Long> {

    // 특정 학습자의 특정 레슨 OCR 결과를 타임스탬프 순으로 조회한다.
    List<OcrResult> findByUserIdAndLessonLessonIdOrderByFrameTimestampSecondAsc(
            Long userId, Long lessonId);

    // 특정 레슨의 OCR 텍스트에서 키워드 검색 시 활용한다.
    List<OcrResult> findByUserIdAndLessonLessonIdAndExtractedTextContainingIgnoreCase(
            Long userId, Long lessonId, String keyword);
}
