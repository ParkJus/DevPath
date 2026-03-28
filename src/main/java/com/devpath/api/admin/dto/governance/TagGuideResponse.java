package com.devpath.api.admin.dto.governance;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TagGuideResponse {

    private List<TagResponse> standardTags;
    private String guideMessage;
}