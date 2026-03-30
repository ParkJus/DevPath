package com.devpath.api.project.service;

import com.devpath.api.project.dto.ProjectAdvancedRequests.MentoringRequest;
import com.devpath.api.project.dto.ProjectAdvancedResponses.MentoringResponse;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.project.entity.MentoringApplication;
import com.devpath.domain.project.entity.MentoringApplicationStatus;
import com.devpath.domain.project.entity.Project;
import com.devpath.domain.project.entity.ProjectStatus;
import com.devpath.domain.project.repository.MentoringApplicationRepository;
import com.devpath.domain.project.repository.ProjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoringApplicationService {

    private final MentoringApplicationRepository mentoringApplicationRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public MentoringResponse applyForMentoring(MentoringRequest request) {
        Project project = getProjectEntity(request.getProjectId());

        validateProjectStatus(project);
        validateDuplicatePendingApplication(request);

        MentoringApplication application = MentoringApplication.builder()
                .projectId(project.getId())
                .mentorId(request.getMentorId())
                .message(request.getMessage())
                .status(MentoringApplicationStatus.PENDING)
                .build();

        return MentoringResponse.from(mentoringApplicationRepository.save(application));
    }

    public List<MentoringResponse> getMentoringApplications(Long projectId) {
        getProjectEntity(projectId);

        return mentoringApplicationRepository.findAllByProjectIdOrderByCreatedAtDesc(projectId).stream()
                .map(MentoringResponse::from)
                .toList();
    }

    private void validateProjectStatus(Project project) {
        if (project.getStatus() == ProjectStatus.COMPLETED || project.getStatus() == ProjectStatus.ON_HOLD) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "현재 프로젝트 상태에서는 멘토링을 신청할 수 없습니다.");
        }
    }

    private void validateDuplicatePendingApplication(MentoringRequest request) {
        if (mentoringApplicationRepository.existsByProjectIdAndMentorIdAndStatus(
                request.getProjectId(),
                request.getMentorId(),
                MentoringApplicationStatus.PENDING
        )) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE, "이미 대기 중인 멘토링 신청이 존재합니다.");
        }
    }

    private Project getProjectEntity(Long projectId) {
        return projectRepository.findByIdAndIsDeletedFalse(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "프로젝트를 찾을 수 없습니다."));
    }
}
