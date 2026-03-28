package com.devpath.api.admin.dto.governance;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NodePrerequisitesRequest {

    private List<Long> prerequisiteNodeIds;
}