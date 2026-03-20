package com.devpath.api.evaluation.service;

import com.devpath.api.evaluation.dto.request.GradeSubmissionRequest;
import com.devpath.api.evaluation.dto.response.SubmissionGradeResponse;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.learning.entity.Rubric;
import com.devpath.domain.learning.entity.Submission;
import com.devpath.domain.learning.repository.RubricRepository;
import com.devpath.domain.learning.repository.SubmissionRepository;
import com.devpath.domain.user.entity.User;
import com.devpath.domain.user.entity.UserRole;
import com.devpath.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionGradingService {

    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final RubricRepository rubricRepository;

    // 제출물을 루브릭 기준으로 채점하고 총점을 계산해 저장한다.
    public SubmissionGradeResponse gradeSubmission(Long userId, Long submissionId, GradeSubmissionRequest request) {
        User instructor = validateInstructor(userId);
        Submission submission = getSubmission(submissionId);

        List<Rubric> rubrics = rubricRepository.findAllByAssignmentIdAndIsDeletedFalseOrderByDisplayOrderAsc(
                submission.getAssignment().getId()
        );

        if (rubrics.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "등록된 루브릭이 없어 채점할 수 없습니다.");
        }

        validateRubricScoreRequest(rubrics, request);

        Map<Long, Rubric> rubricMap = rubrics.stream()
                .collect(Collectors.toMap(Rubric::getId, Function.identity()));

        List<SubmissionGradeResponse.RubricGradeItem> rubricGradeItems = request.getRubricScores().stream()
                .map(scoreRequest -> {
                    Rubric rubric = rubricMap.get(scoreRequest.getRubricId());

                    if (scoreRequest.getEarnedPoints() > rubric.getMaxPoints()) {
                        throw new CustomException(
                                ErrorCode.INVALID_INPUT,
                                "루브릭 '" + rubric.getCriteriaName() + "'의 점수가 최대 점수를 초과했습니다."
                        );
                    }

                    return SubmissionGradeResponse.RubricGradeItem.builder()
                            .rubricId(rubric.getId())
                            .criteriaName(rubric.getCriteriaName())
                            .maxPoints(rubric.getMaxPoints())
                            .earnedPoints(scoreRequest.getEarnedPoints())
                            .build();
                })
                .toList();

        int totalScore = 0;
        for (SubmissionGradeResponse.RubricGradeItem item : rubricGradeItems) {
            totalScore += item.getEarnedPoints();
        }

        // 채점 시작 상태를 거친 뒤 최종 채점 결과를 저장한다.
        submission.startGrading(instructor);
        submission.grade(
                instructor,
                totalScore,
                submission.getIndividualFeedback(),
                submission.getCommonFeedback()
        );

        return SubmissionGradeResponse.builder()
                .submissionId(submission.getId())
                .graderId(instructor.getId())
                .totalScore(submission.getTotalScore())
                .submissionStatus(submission.getSubmissionStatus())
                .gradedAt(submission.getGradedAt())
                .rubricGrades(rubricGradeItems)
                .build();
    }

    // 강사 역할인지 검증한다.
    private User validateInstructor(Long userId) {
        User instructor = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (instructor.getRole() != UserRole.ROLE_INSTRUCTOR) {
            throw new CustomException(ErrorCode.FORBIDDEN, "강사만 제출물을 채점할 수 있습니다.");
        }

        if (!Boolean.TRUE.equals(instructor.getIsActive())) {
            throw new CustomException(ErrorCode.FORBIDDEN, "비활성 사용자입니다.");
        }

        return instructor;
    }

    // 제출 엔티티를 조회한다.
    private Submission getSubmission(Long submissionId) {
        return submissionRepository.findByIdAndIsDeletedFalse(submissionId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "제출물을 찾을 수 없습니다."));
    }

    // 요청된 루브릭 점수 목록이 실제 과제 루브릭과 1대1로 대응하는지 검증한다.
    private void validateRubricScoreRequest(List<Rubric> rubrics, GradeSubmissionRequest request) {
        Set<Long> rubricIds = rubrics.stream()
                .map(Rubric::getId)
                .collect(Collectors.toSet());

        Set<Long> requestedRubricIds = request.getRubricScores().stream()
                .map(GradeSubmissionRequest.RubricScoreRequest::getRubricId)
                .collect(Collectors.toSet());

        if (rubricIds.size() != request.getRubricScores().size()) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "모든 루브릭에 대한 점수를 전달해야 합니다.");
        }

        if (!rubricIds.equals(requestedRubricIds)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "루브릭 점수 요청에 유효하지 않은 rubricId가 포함되어 있습니다.");
        }
    }
}
