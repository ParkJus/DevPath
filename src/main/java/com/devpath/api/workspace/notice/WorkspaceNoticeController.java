package com.devpath.api.workspace.notice;

import com.devpath.api.workspace.notice.dto.NoticeCreateRequest;
import com.devpath.api.workspace.notice.dto.NoticeResponse;
import com.devpath.api.workspace.notice.dto.NoticeUpdateRequest;
import com.devpath.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Workspace Notice", description = "워크스페이스 공지 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkspaceNoticeController {

    // private final WorkspaceNoticeService noticeService; // TODO: Service 구현 후 주석 해제

    @Operation(summary = "워크스페이스 공지 발행", description = "특정 워크스페이스에 새로운 공지를 발행합니다.")
    @PostMapping("/workspaces/{workspaceId}/notices")
    public ResponseEntity<ApiResponse<NoticeResponse>> createNotice(
            @Parameter(description = "워크스페이스 ID") @PathVariable Long workspaceId,
            @Valid @RequestBody NoticeCreateRequest request) {

        // NoticeResponse response = noticeService.createNotice(workspaceId, request);
        // return ResponseEntity.ok(ApiResponse.success(response));
        return null;
    }

    @Operation(summary = "워크스페이스 공지 목록 조회", description = "특정 워크스페이스의 모든 활성 공지 목록을 조회합니다.")
    @GetMapping("/workspaces/{workspaceId}/notices")
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> getNotices(
            @Parameter(description = "워크스페이스 ID") @PathVariable Long workspaceId) {

        // List<NoticeResponse> responses = noticeService.getNoticesByWorkspace(workspaceId);
        // return ResponseEntity.ok(ApiResponse.success(responses));
        return null;
    }

    @Operation(summary = "공지 상세 조회", description = "공지 ID로 특정 공지의 상세 정보를 조회합니다.")
    @GetMapping("/workspace-notices/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNoticeDetail(
            @Parameter(description = "공지 ID") @PathVariable Long noticeId) {

        // NoticeResponse response = noticeService.getNotice(noticeId);
        // return ResponseEntity.ok(ApiResponse.success(response));
        return null;
    }

    @Operation(summary = "공지 수정", description = "특정 공지의 내용을 수정합니다.")
    @PatchMapping("/workspace-notices/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResponse>> updateNotice(
            @Parameter(description = "공지 ID") @PathVariable Long noticeId,
            @Valid @RequestBody NoticeUpdateRequest request) {

        // NoticeResponse response = noticeService.updateNotice(noticeId, request);
        // return ResponseEntity.ok(ApiResponse.success(response));
        return null;
    }

    @Operation(summary = "공지 삭제", description = "특정 공지를 삭제 처리(Soft Delete)합니다.")
    @DeleteMapping("/workspace-notices/{noticeId}")
    public ResponseEntity<ApiResponse<Void>> deleteNotice(
            @Parameter(description = "공지 ID") @PathVariable Long noticeId) {

        // noticeService.deleteNotice(noticeId);
        // return ResponseEntity.ok(ApiResponse.success("공지가 성공적으로 삭제되었습니다.", null));
        return null;
    }
}
