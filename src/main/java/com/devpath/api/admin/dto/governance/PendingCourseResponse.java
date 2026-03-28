package com.devpath.api.admin.dto.governance;

import com.devpath.domain.course.entity.Course;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PendingCourseResponse {

    private Long courseId;
    private Long instructorId;
    private String title;
    private LocalDateTime submittedAt;

    public static PendingCourseResponse from(Course course) {
        return PendingCourseResponse.builder()
                .courseId(course.getCourseId())
                .instructorId(course.getInstructorId())
                .title(course.getTitle())
                .submittedAt(course.getPublishedAt())
                .build();
    }
}