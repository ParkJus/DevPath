package com.devpath.api.project.dto;

import com.devpath.domain.project.entity.ProjectRoleType;
import lombok.Getter;

public class ProjectAdvancedRequests {
    @Getter public static class InvitationRequest {
        private Long projectId;
        private Long inviteeId;
    }
    @Getter public static class RoleRequest {
        private Long projectId;
        private ProjectRoleType roleType;
        private Integer requiredCount;
    }
    @Getter public static class MentoringRequest {
        private Long projectId;
        private Long mentorId;
        private String message;
    }
    @Getter public static class IdeaPostRequest {
        private String title;
        private String content;
    }
    @Getter public static class ProofSubmissionRequest {
        private Long projectId;
        private String proofCardRefId;
    }
}