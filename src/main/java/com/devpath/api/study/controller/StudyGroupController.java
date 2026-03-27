package com.devpath.api.study.controller;

import com.devpath.api.study.dto.StudyGroupRequest;
import com.devpath.api.study.dto.StudyGroupResponse;
import com.devpath.api.study.service.StudyGroupService;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study-groups")
@RequiredArgsConstructor
@Tag(name = "Learner - Study Group", description = "스터디 그룹 모집 및 관리 API")
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @PostMapping
    @Operation(summary = "스터디 그룹 생성", description = "새로운 스터디 그룹을 생성합니다.")
    public ApiResponse<StudyGroupResponse> createStudyGroup(@Valid @RequestBody StudyGroupRequest request) {
        StudyGroupResponse response = studyGroupService.createStudyGroup(request);
        // 수정됨: success -> ok 로 변경
        return ApiResponse.ok(response);
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "스터디 그룹 상세 조회", description = "특정 스터디 그룹의 상세 정보를 조회합니다.")
    public ApiResponse<StudyGroupResponse> getStudyGroup(@PathVariable Long groupId) {
        StudyGroupResponse response = studyGroupService.getStudyGroup(groupId);
        // 수정됨: success -> ok 로 변경
        return ApiResponse.ok(response);
    }

    @GetMapping
    @Operation(summary = "스터디 그룹 목록 조회", description = "전체 스터디 그룹 목록을 조회합니다.")
    public ApiResponse<List<StudyGroupResponse>> getAllStudyGroups() {
        List<StudyGroupResponse> responses = studyGroupService.getAllStudyGroups();
        // 수정됨: success -> ok 로 변경
        return ApiResponse.ok(responses);
    }
}