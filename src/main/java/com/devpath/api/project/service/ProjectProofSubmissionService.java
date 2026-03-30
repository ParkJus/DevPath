package com.devpath.api.project.service;

import com.devpath.api.project.dto.ProjectAdvancedRequests.ProofSubmissionRequest;
import com.devpath.api.project.dto.ProjectAdvancedResponses.ProofSubmissionResponse;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.project.entity.Project;
import com.devpath.domain.project.entity.ProjectProofSubmission;
import com.devpath.domain.project.repository.ProjectProofSubmissionRepository;
import com.devpath.domain.project.repository.ProjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectProofSubmissionService {

    private final ProjectProofSubmissionRepository projectProofSubmissionRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ProofSubmissionResponse submitProof(ProofSubmissionRequest request, Long submitterId) {
        Project project = getProjectEntity(request.getProjectId());

        if (projectProofSubmissionRepository.existsByProjectIdAndProofCardRefId(
                project.getId(),
                request.getProofCardRefId()
        )) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE, "동일한 Proof Card는 같은 프로젝트에 중복 제출할 수 없습니다.");
        }

        ProjectProofSubmission submission = ProjectProofSubmission.builder()
                .projectId(project.getId())
                .submitterId(submitterId)
                .proofCardRefId(request.getProofCardRefId())
                .build();

        return ProofSubmissionResponse.from(projectProofSubmissionRepository.save(submission));
    }

    public List<ProofSubmissionResponse> getSubmissions(Long projectId) {
        getProjectEntity(projectId);

        return projectProofSubmissionRepository.findAllByProjectIdOrderBySubmittedAtDesc(projectId).stream()
                .map(ProofSubmissionResponse::from)
                .toList();
    }

    public ProofSubmissionResponse getSubmissionDetail(Long submissionId) {
        ProjectProofSubmission submission = projectProofSubmissionRepository.findById(submissionId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "프로젝트 제출 이력을 찾을 수 없습니다."));

        return ProofSubmissionResponse.from(submission);
    }

    private Project getProjectEntity(Long projectId) {
        return projectRepository.findByIdAndIsDeletedFalse(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "프로젝트를 찾을 수 없습니다."));
    }
}
