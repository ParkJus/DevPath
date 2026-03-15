package com.devpath.api.instructor.service;

import com.devpath.api.instructor.dto.InstructorAnnouncementDto;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.course.entity.Course;
import com.devpath.domain.course.entity.CourseAnnouncement;
import com.devpath.domain.course.entity.CourseAnnouncementType;
import com.devpath.domain.course.repository.CourseAnnouncementRepository;
import com.devpath.domain.course.repository.CourseRepository;
import com.devpath.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 강사용 공지/새소식 쓰기 로직을 처리한다.
@Service
@RequiredArgsConstructor
public class InstructorAnnouncementService {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final CourseAnnouncementRepository courseAnnouncementRepository;

  // 강의 공지를 생성한다.
  @Transactional
  public Long createAnnouncement(
      Long instructorId,
      Long courseId,
      InstructorAnnouncementDto.CreateAnnouncementRequest request) {
    validateAuthenticatedUser(instructorId);

    Course course = getOwnedCourse(instructorId, courseId);
    validateExposurePeriod(request.getExposureStartAt(), request.getExposureEndAt());

    CourseAnnouncement courseAnnouncement =
        CourseAnnouncement.builder()
            .course(course)
            .type(toAnnouncementType(request.getType()))
            .title(request.getTitle())
            .content(request.getContent())
            .pinned(request.getPinned())
            .displayOrder(request.getDisplayOrder())
            .publishedAt(request.getPublishedAt())
            .exposureStartAt(request.getExposureStartAt())
            .exposureEndAt(request.getExposureEndAt())
            .build();

    CourseAnnouncement savedAnnouncement = courseAnnouncementRepository.save(courseAnnouncement);
    return savedAnnouncement.getAnnouncementId();
  }

  // 강의 공지를 수정한다.
  @Transactional
  public void updateAnnouncement(
      Long instructorId,
      Long announcementId,
      InstructorAnnouncementDto.UpdateAnnouncementRequest request) {
    validateAuthenticatedUser(instructorId);

    CourseAnnouncement courseAnnouncement = getOwnedAnnouncement(instructorId, announcementId);
    validateExposurePeriod(request.getExposureStartAt(), request.getExposureEndAt());

    courseAnnouncement.update(
        toAnnouncementType(request.getType()),
        request.getTitle(),
        request.getContent(),
        request.getPinned(),
        request.getDisplayOrder(),
        request.getPublishedAt(),
        request.getExposureStartAt(),
        request.getExposureEndAt());
  }

  // 강의 공지를 삭제한다.
  @Transactional
  public void deleteAnnouncement(Long instructorId, Long announcementId) {
    validateAuthenticatedUser(instructorId);

    CourseAnnouncement courseAnnouncement = getOwnedAnnouncement(instructorId, announcementId);
    courseAnnouncementRepository.delete(courseAnnouncement);
  }

  // 현재 로그인한 사용자가 존재하는지 검증한다.
  private void validateAuthenticatedUser(Long instructorId) {
    if (instructorId == null) {
      throw new CustomException(ErrorCode.UNAUTHORIZED);
    }

    if (!userRepository.existsById(instructorId)) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }
  }

  // 현재 로그인한 강사가 소유한 강의인지 검증하며 조회한다.
  private Course getOwnedCourse(Long instructorId, Long courseId) {
    return courseRepository
        .findByCourseIdAndInstructorId(courseId, instructorId)
        .orElseGet(
            () -> {
              if (courseRepository.existsById(courseId)) {
                throw new CustomException(ErrorCode.FORBIDDEN);
              }
              throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
            });
  }

  // 현재 로그인한 강사가 소유한 공지인지 검증하며 조회한다.
  private CourseAnnouncement getOwnedAnnouncement(Long instructorId, Long announcementId) {
    return courseAnnouncementRepository
        .findByAnnouncementIdAndCourseInstructorId(announcementId, instructorId)
        .orElseGet(
            () -> {
              if (courseAnnouncementRepository.existsById(announcementId)) {
                throw new CustomException(ErrorCode.FORBIDDEN);
              }
              throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
            });
  }

  // 노출 기간의 시작/종료 시각이 올바른지 검증한다.
  private void validateExposurePeriod(LocalDateTime exposureStartAt, LocalDateTime exposureEndAt) {
    if (exposureStartAt != null
        && exposureEndAt != null
        && exposureStartAt.isAfter(exposureEndAt)) {
      throw new CustomException(ErrorCode.INVALID_INPUT);
    }
  }

  // 문자열 공지 타입을 enum으로 변환한다.
  private CourseAnnouncementType toAnnouncementType(String type) {
    try {
      return CourseAnnouncementType.valueOf(type.trim().toUpperCase(Locale.ROOT));
    } catch (Exception e) {
      throw new CustomException(ErrorCode.INVALID_INPUT);
    }
  }
}
