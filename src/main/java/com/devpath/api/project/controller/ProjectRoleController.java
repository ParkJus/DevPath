package com.devpath.api.project.controller;

import com.devpath.api.project.dto.ProjectAdvancedRequests.RoleRequest;
import com.devpath.api.project.dto.ProjectAdvancedResponses.RoleResponse;
import com.devpath.api.project.service.ProjectRoleService;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/roles")
@RequiredArgsConstructor
@Tag(name = "Project - Role", description = "프로젝트 모집 역할 관리 API")
public class ProjectRoleController {

    private final ProjectRoleService projectRoleService;

    @PostMapping
    @Operation(summary = "역할 추가", description = "프로젝트에 필요한 모집 역할을 추가합니다.")
    public ApiResponse<RoleResponse> addRole(
            @Valid @RequestBody RoleRequest request,
            @RequestParam(defaultValue = "1") Long requesterId
    ) {
        return ApiResponse.ok(projectRoleService.addRole(request, requesterId));
    }

    @PutMapping("/{roleId}")
    @Operation(summary = "역할 수정", description = "모집 역할의 필요 인원 등을 수정합니다.")
    public ApiResponse<RoleResponse> updateRole(
            @PathVariable Long roleId,
            @Valid @RequestBody RoleRequest request,
            @RequestParam(defaultValue = "1") Long requesterId
    ) {
        return ApiResponse.ok(projectRoleService.updateRole(roleId, request, requesterId));
    }
}
