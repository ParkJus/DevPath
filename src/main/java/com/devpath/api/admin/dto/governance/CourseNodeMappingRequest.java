package com.devpath.api.admin.dto.governance;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseNodeMappingRequest {

    @NotEmpty
    private List<Long> nodeIds;
}