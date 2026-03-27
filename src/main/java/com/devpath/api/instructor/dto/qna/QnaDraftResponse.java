package com.devpath.api.instructor.dto.qna;

import com.devpath.api.instructor.entity.QnaAnswerDraft;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QnaDraftResponse {

    private Long id;
    private Long questionId;
    private Long instructorId;
    private String draftContent;
    private LocalDateTime savedAt;
    private LocalDateTime updatedAt;

    public static QnaDraftResponse from(QnaAnswerDraft draft) {
        return QnaDraftResponse.builder()
                .id(draft.getId())
                .questionId(draft.getQuestionId())
                .instructorId(draft.getInstructorId())
                .draftContent(draft.getDraftContent())
                .savedAt(draft.getSavedAt())
                .updatedAt(draft.getUpdatedAt())
                .build();
    }
}