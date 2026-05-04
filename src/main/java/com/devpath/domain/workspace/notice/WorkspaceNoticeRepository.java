package com.devpath.domain.workspace.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceNoticeRepository extends JpaRepository<WorkspaceNotice, Long> {

    // Soft Delete가 적용되지 않은(활성 상태인) 공지사항만 조회
    List<WorkspaceNotice> findByWorkspaceIdAndIsDeletedFalseOrderByCreatedAtDesc(Long workspaceId);

    Optional<WorkspaceNotice> findByIdAndIsDeletedFalse(Long id);

    Optional<WorkspaceNotice> findByIdAndWorkspaceIdAndIsDeletedFalse(Long id, Long workspaceId);

    // 총 활성 공지 개수 카운트
    long countByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
}
