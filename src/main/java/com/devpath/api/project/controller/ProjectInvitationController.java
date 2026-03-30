package com.devpath.api.project.controller;

import com.devpath.api.project.dto.ProjectAdvancedRequests.InvitationRequest;
import com.devpath.api.project.dto.ProjectAdvancedResponses.InvitationResponse;
import com.devpath.api.project.service.ProjectInvitationService;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/invitations")
@RequiredArgsConstructor
@Tag(name = "Project - Invitation", description = "프로젝트 팀원 초대 API")
public class ProjectInvitationController {

    private final ProjectInvitationService projectInvitationService;

    @PostMapping
    @Operation(summary = "팀원 초대", description = "특정 유저를 프로젝트 팀원으로 초대합니다.")
    public ApiResponse<InvitationResponse> inviteMember(
            @Valid @RequestBody InvitationRequest request,
            @RequestParam(defaultValue = "1") Long inviterId
    ) {
        return ApiResponse.ok(projectInvitationService.inviteMember(request, inviterId));
    }

    @PostMapping("/{invitationId}/accept")
    @Operation(summary = "초대 수락", description = "받은 프로젝트 초대를 수락합니다.")
    public ApiResponse<InvitationResponse> acceptInvitation(@PathVariable Long invitationId) {
        return ApiResponse.ok(projectInvitationService.acceptInvitation(invitationId));
    }

    @PostMapping("/{invitationId}/reject")
    @Operation(summary = "초대 거절", description = "받은 프로젝트 초대를 거절합니다.")
    public ApiResponse<InvitationResponse> rejectInvitation(@PathVariable Long invitationId) {
        return ApiResponse.ok(projectInvitationService.rejectInvitation(invitationId));
    }
}
