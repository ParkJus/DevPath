package com.devpath.domain.course.repository;

import com.devpath.domain.course.entity.CourseAnnouncement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// 강의 공지/새소식 조회와 저장을 담당한다.
public interface CourseAnnouncementRepository extends JpaRepository<CourseAnnouncement, Long> {

  // 특정 강의의 공지 목록을 조회한다.
  List<CourseAnnouncement> findAllByCourseCourseIdOrderByPinnedDescDisplayOrderAscAnnouncementIdDesc(
      Long courseId);

  // 현재 로그인한 강사가 소유한 공지인지 검증하며 조회한다.
  Optional<CourseAnnouncement> findByAnnouncementIdAndCourseInstructorId(
      Long announcementId, Long instructorId);

  // 특정 강의의 공지를 모두 삭제한다.
  void deleteAllByCourseCourseId(Long courseId);
}
