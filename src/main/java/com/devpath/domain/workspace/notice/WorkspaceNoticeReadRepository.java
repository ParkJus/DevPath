package com.devpath.domain.workspace.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceNoticeReadRepository extends JpaRepository<WorkspaceNoticeRead, Long> {

    // 특정 유저가 특정 공지를 읽었는지 확인
    boolean existsByNoticeIdAndUserId(Long noticeId, Long userId);

    // 특정 유저가 특정 워크스페이스에서 읽은 공지 개수 카운트
    long countByWorkspaceIdAndUserId(Long workspaceId, Long userId);
}
