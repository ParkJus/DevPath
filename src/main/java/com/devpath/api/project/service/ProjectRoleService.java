package com.devpath.api.project.service;

import com.devpath.api.project.dto.ProjectAdvancedRequests.RoleRequest;
import com.devpath.api.project.dto.ProjectAdvancedResponses.RoleResponse;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.project.entity.Project;
import com.devpath.domain.project.entity.ProjectRole;
import com.devpath.domain.project.repository.ProjectRepository;
import com.devpath.domain.project.repository.ProjectRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectRoleService {

    private final ProjectRoleRepository projectRoleRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public RoleResponse addRole(RoleRequest request) {
        Project project = getProjectEntity(request.getProjectId());

        ProjectRole projectRole = ProjectRole.builder()
                .projectId(project.getId())
                .roleType(request.getRoleType())
                .requiredCount(request.getRequiredCount())
                .build();

        return RoleResponse.from(projectRoleRepository.save(projectRole));
    }

    @Transactional
    public RoleResponse updateRole(Long roleId, RoleRequest request) {
        getProjectEntity(request.getProjectId());

        ProjectRole projectRole = projectRoleRepository.findByIdAndProjectId(roleId, request.getProjectId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "프로젝트 역할 정보를 찾을 수 없습니다."));

        projectRole.updateCount(request.getRequiredCount());
        return RoleResponse.from(projectRole);
    }

    private Project getProjectEntity(Long projectId) {
        return projectRepository.findByIdAndIsDeletedFalse(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "프로젝트를 찾을 수 없습니다."));
    }
}
