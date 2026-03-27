package com.devpath.domain.qna.repository;

import com.devpath.domain.qna.entity.Question;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // 삭제되지 않은 질문만 최신순으로 조회한다.
    List<Question> findAllByIsDeletedFalseOrderByCreatedAtDesc();

    // 삭제되지 않은 질문만 단건 조회한다.
    Optional<Question> findByIdAndIsDeletedFalse(Long questionId);

    // 제목 키워드가 포함된 질문을 최신순으로 제한 조회한다.
    List<Question> findTop10ByIsDeletedFalseAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(String titleKeyword);

    // 강사 강의 기반으로 QnA Inbox 질문을 조회한다.
    @Query("SELECT q FROM Question q WHERE q.courseId IN (SELECT c.courseId FROM Course c WHERE c.instructorId = :instructorId) AND q.isDeleted = false ORDER BY q.createdAt DESC")
    List<Question> findAllByInstructorIdAndIsDeletedFalse(@Param("instructorId") Long instructorId);

    // 강사 강의 기반 + 상태 필터 조회한다.
    @Query("SELECT q FROM Question q WHERE q.courseId IN (SELECT c.courseId FROM Course c WHERE c.instructorId = :instructorId) AND q.isDeleted = false AND q.qnaStatus = :status ORDER BY q.createdAt DESC")
    List<Question> findAllByInstructorIdAndQnaStatusAndIsDeletedFalse(@Param("instructorId") Long instructorId, @Param("status") String status);
}
