package com.devpath.api.admin.dto.governance;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CourseNodeMappingCandidateResponse {

    private Long courseId;
    private String courseTitle;
    private List<Long> suggestedNodeIds;
    private Double tagMatchRate;
}