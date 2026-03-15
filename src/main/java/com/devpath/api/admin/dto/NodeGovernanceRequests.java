package com.devpath.api.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class NodeGovernanceRequests {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "노드 필수 태그 변경 요청 DTO")
    public static class UpdateRequiredTags {
        @Schema(description = "필수 태그 ID 목록")
        private List<Long> tagIds;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "노드 타입 변경 요청 DTO")
    public static class UpdateNodeType {
        @Schema(description = "변경할 노드 타입 (예: CONCEPT, PRACTICE, PROJECT, REVIEW, EXAM)")
        private String nodeType;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "노드 선행 조건 변경 요청 DTO")
    public static class UpdatePrerequisites {
        @Schema(description = "선행해야 할 노드 ID 목록")
        private List<Long> prerequisiteNodeIds;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "노드 완료 기준 변경 요청 DTO")
    public static class UpdateCompletionRule {
        @Schema(description = "조건 유형 (예: TAG_COVERAGE, COURSE_COMPLETION, QUIZ_PASS)")
        private String criteriaType;

        @Schema(description = "조건 값 (예: 100, tag_id_list 등)")
        private String criteriaValue;
    }
}