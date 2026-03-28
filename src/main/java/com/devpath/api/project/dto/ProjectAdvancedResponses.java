package com.devpath.api.project.dto;

import com.devpath.domain.project.entity.*;
import lombok.Builder;
import lombok.Getter;

public class ProjectAdvancedResponses {
    @Getter @Builder public static class InvitationResponse {
        private Long id;
        private ProjectInvitationStatus status;
        public static InvitationResponse from(ProjectInvitation inv) {
            return builder().id(inv.getId()).status(inv.getStatus()).build();
        }
    }
    @Getter @Builder public static class RoleResponse {
        private Long id;
        private ProjectRoleType roleType;
        private Integer requiredCount;
        public static RoleResponse from(ProjectRole role) {
            return builder().id(role.getId()).roleType(role.getRoleType()).requiredCount(role.getRequiredCount()).build();
        }
    }
    @Getter @Builder public static class IdeaPostResponse {
        private Long id;
        private String title;
        private String content;
        public static IdeaPostResponse from(ProjectIdeaPost post) {
            return builder().id(post.getId()).title(post.getTitle()).content(post.getContent()).build();
        }
    }
}