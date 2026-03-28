package com.devpath.api.instructor.repository;

import com.devpath.api.instructor.entity.QnaAnswerDraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnaAnswerDraftRepository extends JpaRepository<QnaAnswerDraft, Long> {

    Optional<QnaAnswerDraft> findByQuestionIdAndInstructorIdAndIsDeletedFalse(Long questionId, Long instructorId);
}