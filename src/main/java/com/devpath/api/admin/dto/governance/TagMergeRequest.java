package com.devpath.api.admin.dto.governance;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagMergeRequest {

    @NotEmpty
    private List<Long> sourceTagIds;

    @NotNull
    private Long targetTagId;
}