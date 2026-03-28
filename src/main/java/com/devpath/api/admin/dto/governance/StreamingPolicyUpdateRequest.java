package com.devpath.api.admin.dto.governance;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamingPolicyUpdateRequest {

    private Boolean hlsEnabled;
    private String maxResolution;
    private Boolean watermarkEnabled;
}