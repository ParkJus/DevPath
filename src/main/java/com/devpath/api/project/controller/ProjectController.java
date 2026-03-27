package com.devpath.api.project.controller;

import com.devpath.api.project.dto.ProjectRequest;
import com.devpath.api.project.dto.ProjectResponse;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project - Core", description = "프로젝트 생성 및 관리 API")
public class ProjectController {

    // Note: ProjectService는 앞선 단계에서 생략했으므로, 우선 형태(Stub)만 잡아둡니다.
    // 추후 Service 구현체가 완성되면 연동합니다.

    @PostMapping
    @Operation(summary = "프로젝트 생성", description = "새로운 팀 프로젝트를 생성합니다.")
    public ApiResponse<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request) {
        // TODO: ProjectService.createProject(request) 연동
        return ApiResponse.ok(null);
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "프로젝트 상세 조회", description = "특정 프로젝트의 상세 정보를 조회합니다.")
    public ApiResponse<ProjectResponse> getProject(@PathVariable Long projectId) {
        // TODO: ProjectService.getProject(projectId) 연동
        return ApiResponse.ok(null);
    }
}