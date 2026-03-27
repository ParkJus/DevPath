package com.devpath.api.instructor.dto.qna;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaStatusUpdateRequest {

    @NotNull
    private String status;
}