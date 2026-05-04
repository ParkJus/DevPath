package com.devpath.domain.workspace.notice;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "workspace_notice_reads",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"notice_id", "user_id"})
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class WorkspaceNoticeRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "notice_id", nullable = false)
    private Long noticeId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreatedDate
    @Column(name = "read_at", updatable = false)
    private LocalDateTime readAt;

    @Builder
    public WorkspaceNoticeRead(Long workspaceId, Long noticeId, Long userId) {
        this.workspaceId = workspaceId;
        this.noticeId = noticeId;
        this.userId = userId;
    }
}
