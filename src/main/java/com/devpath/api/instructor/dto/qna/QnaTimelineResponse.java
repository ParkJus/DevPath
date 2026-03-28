package com.devpath.api.instructor.dto.qna;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QnaTimelineResponse {

    private QnaInboxResponse question;
    private List<QnaAnswerResponse> answers;
    private QnaDraftResponse draft;
    private String lectureTitle;
    private String lectureTimestamp;
}