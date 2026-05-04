package com.devpath.domain.workspace.integration;

import com.devpath.api.workspace.integration.dto.IntegrationResponse;
import com.devpath.api.workspace.integration.dto.IntegrationStatusUpdateRequest;
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
public class ExternalIntegrationService {

    private final ExternalIntegrationRepository integrationRepository;

    public List<IntegrationResponse> getIntegrationsByWorkspace(Long workspaceId) {
        return integrationRepository.findByWorkspaceId(workspaceId)
                .stream()
                .map(IntegrationResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public IntegrationResponse updateIntegrationStatus(Long workspaceId, IntegrationProvider provider, IntegrationStatusUpdateRequest request) {
        ExternalIntegration integration = integrationRepository.findByWorkspaceIdAndProvider(workspaceId, provider)
                .orElseThrow(() -> new CustomException(ErrorCode.INTEGRATION_NOT_FOUND));

        // 비즈니스 메서드를 통한 상태 변경
        if (request.getIsActive()) {
            integration.activate();
        } else {
            integration.deactivate();
        }

        return IntegrationResponse.from(integration);
    }
}
