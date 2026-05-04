package com.devpath.domain.workspace.notice;

import com.devpath.api.workspace.notice.dto.NoticeCreateRequest;
import com.devpath.api.workspace.notice.dto.NoticeResponse;
import com.devpath.api.workspace.notice.dto.NoticeUpdateRequest;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceNoticeService {

    private final WorkspaceNoticeRepository noticeRepository;
    private final WorkspaceNoticeReadRepository noticeReadRepository;

    @Transactional
    public NoticeResponse createNotice(Long workspaceId, NoticeCreateRequest request) {
        WorkspaceNotice notice = WorkspaceNotice.builder()
                .workspaceId(workspaceId)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        WorkspaceNotice savedNotice = noticeRepository.save(notice);
        return NoticeResponse.from(savedNotice);
    }

    public List<NoticeResponse> getNoticesByWorkspace(Long workspaceId) {
        return noticeRepository.findByWorkspaceIdAndIsDeletedFalseOrderByCreatedAtDesc(workspaceId)
                .stream()
                .map(NoticeResponse::from)
                .collect(Collectors.toList());
    }

    public NoticeResponse getNotice(Long noticeId) {
        WorkspaceNotice notice = noticeRepository.findByIdAndIsDeletedFalse(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
        return NoticeResponse.from(notice);
    }

    @Transactional
    public NoticeResponse updateNotice(Long noticeId, NoticeUpdateRequest request) {
        WorkspaceNotice notice = noticeRepository.findByIdAndIsDeletedFalse(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));

        notice.updateNotice(request.getTitle(), request.getContent());
        return NoticeResponse.from(notice);
    }

    @Transactional
    public void deleteNotice(Long noticeId) {
        WorkspaceNotice notice = noticeRepository.findByIdAndIsDeletedFalse(noticeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));

        notice.delete();
    }

    @Transactional
    public void markAsRead(Long workspaceId, Long noticeId, Long userId) {
        WorkspaceNotice notice = noticeRepository.findByIdAndWorkspaceIdAndIsDeletedFalse(noticeId, workspaceId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));

        boolean alreadyRead = noticeReadRepository.existsByNoticeIdAndUserId(notice.getId(), userId);
        if (!alreadyRead) {
            WorkspaceNoticeRead noticeRead = WorkspaceNoticeRead.builder()
                    .workspaceId(workspaceId)
                    .noticeId(notice.getId())
                    .userId(userId)
                    .build();
            noticeReadRepository.save(noticeRead);
        }
    }

    public List<NoticeResponse> getUnreadNotices(Long workspaceId, Long userId) {
        List<WorkspaceNotice> allNotices = noticeRepository.findByWorkspaceIdAndIsDeletedFalseOrderByCreatedAtDesc(workspaceId);

        return allNotices.stream()
                .filter(notice -> !noticeReadRepository.existsByNoticeIdAndUserId(notice.getId(), userId))
                .map(NoticeResponse::from)
                .collect(Collectors.toList());
    }

    public long getUnreadNoticeCount(Long workspaceId, Long userId) {
        long totalNotices = noticeRepository.countByWorkspaceIdAndIsDeletedFalse(workspaceId);
        long readNotices = noticeReadRepository.countByWorkspaceIdAndUserId(workspaceId, userId);

        return Math.max(0, totalNotices - readNotices);
    }
}
