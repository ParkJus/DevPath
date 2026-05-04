package com.devpath.domain.workspace.integration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExternalIntegrationRepository extends JpaRepository<ExternalIntegration, Long> {

    // 특정 워크스페이스의 모든 연동 상태 조회
    List<ExternalIntegration> findByWorkspaceId(Long workspaceId);

    // 특정 워크스페이스의 특정 프로바이더 연동 상태 단건 조회
    Optional<ExternalIntegration> findByWorkspaceIdAndProvider(Long workspaceId, IntegrationProvider provider);
}
