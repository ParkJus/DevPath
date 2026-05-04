package com.devpath.domain.workspace.integration;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "external_integrations",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"workspace_id", "provider"})
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ExternalIntegration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private IntegrationProvider provider;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "connected_at")
    private LocalDateTime connectedAt;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public ExternalIntegration(Long workspaceId, IntegrationProvider provider) {
        this.workspaceId = workspaceId;
        this.provider = provider;
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
        this.connectedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.isActive = false;
    }
}
