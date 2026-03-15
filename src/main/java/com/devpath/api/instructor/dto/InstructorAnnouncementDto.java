package com.devpath.api.instructor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// 강사용 공지/새소식 CRUD DTO를 제공한다.
public class InstructorAnnouncementDto {

  // 공지 생성 요청 DTO다.
  @Getter
  @Schema(description = "강의 공지 생성 요청 DTO")
  public static class CreateAnnouncementRequest {

    @NotBlank(message = "공지 타입은 필수입니다.")
    @Schema(
        description = "공지 타입",
        example = "notice",
        allowableValues = {"notice", "news", "event"})
    private String type;

    @NotBlank(message = "공지 제목은 필수입니다.")
    @Schema(description = "공지 제목", example = "Spring Security 강의 업데이트 안내")
    private String title;

    @NotBlank(message = "공지 내용은 필수입니다.")
    @Schema(description = "공지 내용", example = "3강과 4강 자료가 추가되었습니다.")
    private String content;

    @NotNull(message = "고정 여부는 필수입니다.")
    @Schema(description = "상단 고정 여부", example = "false")
    private Boolean pinned;

    @NotNull(message = "노출 순서는 필수입니다.")
    @PositiveOrZero(message = "노출 순서는 0 이상이어야 합니다.")
    @Schema(description = "노출 순서", example = "0")
    private Integer displayOrder;

    @Schema(description = "게시 시각", example = "2026-03-16T10:00:00")
    private LocalDateTime publishedAt;

    @Schema(description = "노출 시작 시각", example = "2026-03-16T10:00:00")
    private LocalDateTime exposureStartAt;

    @Schema(description = "노출 종료 시각", example = "2026-03-30T23:59:59")
    private LocalDateTime exposureEndAt;
  }

  // 공지 수정 요청 DTO다.
  @Getter
  @Schema(description = "강의 공지 수정 요청 DTO")
  public static class UpdateAnnouncementRequest {

    @NotBlank(message = "공지 타입은 필수입니다.")
    @Schema(
        description = "공지 타입",
        example = "news",
        allowableValues = {"notice", "news", "event"})
    private String type;

    @NotBlank(message = "공지 제목은 필수입니다.")
    @Schema(description = "공지 제목", example = "Spring Security 강의 소식")
    private String title;

    @NotBlank(message = "공지 내용은 필수입니다.")
    @Schema(description = "공지 내용", example = "실습 예제가 최신 버전 기준으로 수정되었습니다.")
    private String content;

    @NotNull(message = "고정 여부는 필수입니다.")
    @Schema(description = "상단 고정 여부", example = "true")
    private Boolean pinned;

    @NotNull(message = "노출 순서는 필수입니다.")
    @PositiveOrZero(message = "노출 순서는 0 이상이어야 합니다.")
    @Schema(description = "노출 순서", example = "1")
    private Integer displayOrder;

    @Schema(description = "게시 시각", example = "2026-03-16T10:00:00")
    private LocalDateTime publishedAt;

    @Schema(description = "노출 시작 시각", example = "2026-03-16T10:00:00")
    private LocalDateTime exposureStartAt;

    @Schema(description = "노출 종료 시각", example = "2026-03-31T23:59:59")
    private LocalDateTime exposureEndAt;
  }

  // 공지 목록 응답 DTO다.
  @Getter
  @Builder
  @AllArgsConstructor
  @Schema(description = "강의 공지 목록 응답 DTO")
  public static class AnnouncementSummaryResponse {

    @Schema(description = "공지 ID", example = "11")
    private Long announcementId;

    @Schema(description = "강의 ID", example = "3")
    private Long courseId;

    @Schema(description = "공지 타입", example = "NOTICE")
    private String type;

    @Schema(description = "공지 제목", example = "Spring Security 강의 업데이트 안내")
    private String title;

    @Schema(description = "상단 고정 여부", example = "false")
    private Boolean pinned;

    @Schema(description = "노출 순서", example = "0")
    private Integer displayOrder;

    @Schema(description = "게시 시각", example = "2026-03-16T10:00:00")
    private LocalDateTime publishedAt;

    @Schema(description = "노출 시작 시각", example = "2026-03-16T10:00:00")
    private LocalDateTime exposureStartAt;

    @Schema(description = "노출 종료 시각", example = "2026-03-30T23:59:59")
    private LocalDateTime exposureEndAt;
  }

  // 공지 상세 응답 DTO다.
  @Getter
  @Builder
  @AllArgsConstructor
  @Schema(description = "강의 공지 상세 응답 DTO")
  public static class AnnouncementDetailResponse {

    @Schema(description = "공지 ID", example = "11")
    private Long announcementId;

    @Schema(description = "강의 ID", example = "3")
    private Long courseId;

    @Schema(description = "공지 타입", example = "NOTICE")
    private String type;

    @Schema(description = "공지 제목", example = "Spring Security 강의 업데이트 안내")
    private String title;

    @Schema(description = "공지 내용", example = "3강과 4강 자료가 추가되었습니다.")
    private String content;

    @Schema(description = "상단 고정 여부", example = "false")
    private Boolean pinned;

    @Schema(description = "노출 순서", example = "0")
    private Integer displayOrder;

    @Schema(description = "게시 시각", example = "2026-03-16T10:00:00")
    private LocalDateTime publishedAt;

    @Schema(description = "노출 시작 시각", example = "2026-03-16T10:00:00")
    private LocalDateTime exposureStartAt;

    @Schema(description = "노출 종료 시각", example = "2026-03-30T23:59:59")
    private LocalDateTime exposureEndAt;

    @Schema(description = "생성 시각", example = "2026-03-16T09:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정 시각", example = "2026-03-16T09:40:00")
    private LocalDateTime updatedAt;
  }
}
