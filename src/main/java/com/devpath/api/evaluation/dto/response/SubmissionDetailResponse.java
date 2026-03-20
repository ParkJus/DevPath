package com.devpath.api.evaluation.dto.response;

import com.devpath.domain.learning.entity.Rubric;
import com.devpath.domain.learning.entity.Submission;
import com.devpath.domain.learning.entity.SubmissionFile;
import com.devpath.domain.learning.entity.SubmissionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "제출물 상세 조회 응답 DTO")
public class SubmissionDetailResponse {

    // 제출 ID다.
    @Schema(description = "제출 ID", example = "1")
    private Long submissionId;

    // 과제 ID다.
    @Schema(description = "과제 ID", example = "10")
    private Long assignmentId;

    // 과제 제목이다.
    @Schema(description = "과제 제목", example = "Spring Security 실습 과제")
    private String assignmentTitle;

    // 학습자 ID다.
    @Schema(description = "학습자 ID", example = "2")
    private Long learnerId;

    // 학습자 이름이다.
    @Schema(description = "학습자 이름", example = "김태형")
    private String learnerName;

    // 현재 제출 상태다.
    @Schema(description = "제출 상태", example = "SUBMITTED")
    private SubmissionStatus submissionStatus;

    // 제출 텍스트다.
    @Schema(description = "제출 텍스트", example = "구현 요약 및 실행 결과를 첨부했습니다.")
    private String submissionText;

    // 제출 URL이다.
    @Schema(description = "제출 URL", example = "https://github.com/example/devpath-assignment")
    private String submissionUrl;

    // 지각 제출 여부다.
    @Schema(description = "지각 제출 여부", example = "false")
    private Boolean isLate;

    // 제출 시각이다.
    @Schema(description = "제출 시각", example = "2026-03-20T12:00:00")
    private LocalDateTime submittedAt;

    // 채점 시각이다.
    @Schema(description = "채점 시각", example = "2026-03-20T13:00:00")
    private LocalDateTime gradedAt;

    // README 검증 통과 여부다.
    @Schema(description = "README 통과 여부", example = "true")
    private Boolean readmePassed;

    // 테스트 검증 통과 여부다.
    @Schema(description = "테스트 통과 여부", example = "true")
    private Boolean testPassed;

    // 린트 검증 통과 여부다.
    @Schema(description = "린트 통과 여부", example = "true")
    private Boolean lintPassed;

    // 파일 형식 검증 통과 여부다.
    @Schema(description = "파일 형식 통과 여부", example = "true")
    private Boolean fileFormatPassed;

    // 자동검증 품질 점수다.
    @Schema(description = "품질 점수", example = "100")
    private Integer qualityScore;

    // 최종 채점 점수다.
    @Schema(description = "최종 점수", example = "85")
    private Integer totalScore;

    // 개별 피드백이다.
    @Schema(description = "개별 피드백", example = "전반적으로 구현이 안정적입니다.")
    private String individualFeedback;

    // 공통 피드백이다.
    @Schema(description = "공통 피드백", example = "README는 실행 방법을 더 구체적으로 적어주세요.")
    private String commonFeedback;

    // 제출 파일 목록이다.
    @Schema(description = "제출 파일 목록")
    private List<FileItem> files = new ArrayList<>();

    // 연결된 루브릭 목록이다.
    @Schema(description = "루브릭 목록")
    private List<RubricItem> rubrics = new ArrayList<>();

    @Builder
    public SubmissionDetailResponse(
            Long submissionId,
            Long assignmentId,
            String assignmentTitle,
            Long learnerId,
            String learnerName,
            SubmissionStatus submissionStatus,
            String submissionText,
            String submissionUrl,
            Boolean isLate,
            LocalDateTime submittedAt,
            LocalDateTime gradedAt,
            Boolean readmePassed,
            Boolean testPassed,
            Boolean lintPassed,
            Boolean fileFormatPassed,
            Integer qualityScore,
            Integer totalScore,
            String individualFeedback,
            String commonFeedback,
            List<FileItem> files,
            List<RubricItem> rubrics
    ) {
        this.submissionId = submissionId;
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.learnerId = learnerId;
        this.learnerName = learnerName;
        this.submissionStatus = submissionStatus;
        this.submissionText = submissionText;
        this.submissionUrl = submissionUrl;
        this.isLate = isLate;
        this.submittedAt = submittedAt;
        this.gradedAt = gradedAt;
        this.readmePassed = readmePassed;
        this.testPassed = testPassed;
        this.lintPassed = lintPassed;
        this.fileFormatPassed = fileFormatPassed;
        this.qualityScore = qualityScore;
        this.totalScore = totalScore;
        this.individualFeedback = individualFeedback;
        this.commonFeedback = commonFeedback;
        this.files = files == null ? new ArrayList<>() : files;
        this.rubrics = rubrics == null ? new ArrayList<>() : rubrics;
    }

    // 제출 엔티티와 루브릭 목록을 상세 응답 DTO로 변환한다.
    public static SubmissionDetailResponse of(Submission submission, List<Rubric> rubricList) {
        List<FileItem> fileItems = submission.getFiles() == null
                ? new ArrayList<>()
                : submission.getFiles().stream()
                        .map(FileItem::from)
                        .toList();

        List<RubricItem> rubricItems = rubricList == null
                ? new ArrayList<>()
                : rubricList.stream()
                        .map(RubricItem::from)
                        .toList();

        return SubmissionDetailResponse.builder()
                .submissionId(submission.getId())
                .assignmentId(submission.getAssignment().getId())
                .assignmentTitle(submission.getAssignment().getTitle())
                .learnerId(submission.getLearner().getId())
                .learnerName(submission.getLearner().getName())
                .submissionStatus(submission.getSubmissionStatus())
                .submissionText(submission.getSubmissionText())
                .submissionUrl(submission.getSubmissionUrl())
                .isLate(submission.getIsLate())
                .submittedAt(submission.getSubmittedAt())
                .gradedAt(submission.getGradedAt())
                .readmePassed(submission.getReadmePassed())
                .testPassed(submission.getTestPassed())
                .lintPassed(submission.getLintPassed())
                .fileFormatPassed(submission.getFileFormatPassed())
                .qualityScore(submission.getQualityScore())
                .totalScore(submission.getTotalScore())
                .individualFeedback(submission.getIndividualFeedback())
                .commonFeedback(submission.getCommonFeedback())
                .files(fileItems)
                .rubrics(rubricItems)
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "제출 파일 항목 DTO")
    public static class FileItem {

        // 파일 ID다.
        @Schema(description = "파일 ID", example = "1")
        private Long fileId;

        // 파일명이다.
        @Schema(description = "파일명", example = "README.md")
        private String fileName;

        // 파일 URL이다.
        @Schema(description = "파일 URL", example = "https://s3.example.com/devpath/README.md")
        private String fileUrl;

        // 파일 크기다.
        @Schema(description = "파일 크기(byte)", example = "2048")
        private Long fileSize;

        // 파일 타입이다.
        @Schema(description = "파일 타입", example = "md")
        private String fileType;

        @Builder
        public FileItem(Long fileId, String fileName, String fileUrl, Long fileSize, String fileType) {
            this.fileId = fileId;
            this.fileName = fileName;
            this.fileUrl = fileUrl;
            this.fileSize = fileSize;
            this.fileType = fileType;
        }

        // 제출 파일 엔티티를 DTO로 변환한다.
        public static FileItem from(SubmissionFile file) {
            return FileItem.builder()
                    .fileId(file.getId())
                    .fileName(file.getFileName())
                    .fileUrl(file.getFileUrl())
                    .fileSize(file.getFileSize())
                    .fileType(file.getFileType())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(description = "루브릭 항목 DTO")
    public static class RubricItem {

        // 루브릭 ID다.
        @Schema(description = "루브릭 ID", example = "1")
        private Long rubricId;

        // 루브릭 기준명이다.
        @Schema(description = "루브릭 기준명", example = "기능 구현 완성도")
        private String criteriaName;

        // 루브릭 설명이다.
        @Schema(description = "루브릭 설명", example = "필수 기능을 정확하게 구현했는지 평가합니다.")
        private String criteriaDescription;

        // 최대 점수다.
        @Schema(description = "최대 점수", example = "10")
        private Integer maxPoints;

        // 노출 순서다.
        @Schema(description = "노출 순서", example = "1")
        private Integer displayOrder;

        @Builder
        public RubricItem(
                Long rubricId,
                String criteriaName,
                String criteriaDescription,
                Integer maxPoints,
                Integer displayOrder
        ) {
            this.rubricId = rubricId;
            this.criteriaName = criteriaName;
            this.criteriaDescription = criteriaDescription;
            this.maxPoints = maxPoints;
            this.displayOrder = displayOrder;
        }

        // 루브릭 엔티티를 DTO로 변환한다.
        public static RubricItem from(Rubric rubric) {
            return RubricItem.builder()
                    .rubricId(rubric.getId())
                    .criteriaName(rubric.getCriteriaName())
                    .criteriaDescription(rubric.getCriteriaDescription())
                    .maxPoints(rubric.getMaxPoints())
                    .displayOrder(rubric.getDisplayOrder())
                    .build();
        }
    }
}
