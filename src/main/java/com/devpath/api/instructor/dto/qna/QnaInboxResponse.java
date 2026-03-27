package com.devpath.api.instructor.dto.qna;

import com.devpath.domain.qna.entity.Question;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QnaInboxResponse {

    private Long questionId;
    private Long courseId;
    private Long learnerId;
    private String title;
    private String content;
    private String status;
    private String lectureTimestamp;
    private LocalDateTime createdAt;

    public static QnaInboxResponse from(Question question) {
        return QnaInboxResponse.builder()
                .questionId(question.getId())
                .courseId(question.getCourseId())
                .learnerId(question.getUser().getId())
                .title(question.getTitle())
                .content(question.getContent())
                .status(question.getQnaStatus())
                .lectureTimestamp(question.getLectureTimestamp())
                .createdAt(question.getCreatedAt())
                .build();
    }
}