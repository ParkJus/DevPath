package com.devpath.api.project.service;

import com.devpath.api.project.dto.ProjectAdvancedRequests.InvitationRequest;
import com.devpath.api.project.dto.ProjectAdvancedResponses.InvitationResponse;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.project.entity.Project;
import com.devpath.domain.project.entity.ProjectInvitation;
import com.devpath.domain.project.entity.ProjectInvitationStatus;
import com.devpath.domain.project.entity.ProjectMember;
import com.devpath.domain.project.entity.ProjectRoleType;
import com.devpath.domain.project.repository.ProjectInvitationRepository;
import com.devpath.domain.project.repository.ProjectMemberRepository;
import com.devpath.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectInvitationService {

    private final ProjectInvitationRepository projectInvitationRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Transactional
    public InvitationResponse inviteMember(InvitationRequest request, Long inviterId) {
        Project project = getProjectEntity(request.getProjectId());

        ProjectInvitation invitation = ProjectInvitation.builder()
                .projectId(project.getId())
                .inviterId(inviterId)
                .inviteeId(request.getInviteeId())
                .status(ProjectInvitationStatus.PENDING)
                .build();

        return InvitationResponse.from(projectInvitationRepository.save(invitation));
    }

    @Transactional
    public InvitationResponse acceptInvitation(Long invitationId) {
        ProjectInvitation invitation = getInvitationEntity(invitationId);

        if (invitation.getStatus() != ProjectInvitationStatus.PENDING) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "대기 중인 초대만 수락할 수 있습니다.");
        }

        getProjectEntity(invitation.getProjectId());
        invitation.accept();

        if (!projectMemberRepository.existsByProjectIdAndLearnerId(invitation.getProjectId(), invitation.getInviteeId())) {
            ProjectMember projectMember = ProjectMember.builder()
                    .projectId(invitation.getProjectId())
                    .learnerId(invitation.getInviteeId())
                    .roleType(ProjectRoleType.FULLSTACK)
                    .build();
            projectMemberRepository.save(projectMember);
        }

        return InvitationResponse.from(invitation);
    }

    @Transactional
    public InvitationResponse rejectInvitation(Long invitationId) {
        ProjectInvitation invitation = getInvitationEntity(invitationId);

        if (invitation.getStatus() != ProjectInvitationStatus.PENDING) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "대기 중인 초대만 거절할 수 있습니다.");
        }

        invitation.reject();
        return InvitationResponse.from(invitation);
    }

    private ProjectInvitation getInvitationEntity(Long invitationId) {
        return projectInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "프로젝트 초대를 찾을 수 없습니다."));
    }

    private Project getProjectEntity(Long projectId) {
        return projectRepository.findByIdAndIsDeletedFalse(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "프로젝트를 찾을 수 없습니다."));
    }
}
