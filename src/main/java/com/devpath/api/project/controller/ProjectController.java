package com.devpath.api.project.controller;

import com.devpath.api.project.dto.ProjectRequest;
import com.devpath.api.project.dto.ProjectResponse;
import com.devpath.api.project.service.ProjectService;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project - Core", description = "프로젝트 생성 및 관리 API")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "프로젝트 생성", description = "새로운 팀 프로젝트를 생성합니다.")
    public ApiResponse<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request,
            @RequestParam(defaultValue = "1") Long creatorId
    ) {
        return ApiResponse.ok(projectService.createProject(request, creatorId));
    }

    @GetMapping
    @Operation(summary = "프로젝트 목록 조회", description = "전체 프로젝트 목록을 조회합니다.")
    public ApiResponse<List<ProjectResponse>> getAllProjects() {
        return ApiResponse.ok(projectService.getAllProjects());
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "프로젝트 상세 조회", description = "특정 프로젝트의 상세 정보를 조회합니다.")
    public ApiResponse<ProjectResponse> getProject(@PathVariable Long projectId) {
        return ApiResponse.ok(projectService.getProject(projectId));
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "프로젝트 수정", description = "기존 프로젝트의 정보를 수정합니다.")
    public ApiResponse<ProjectResponse> updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectRequest request
    ) {
        return ApiResponse.ok(projectService.updateProject(projectId, request));
    }
}
