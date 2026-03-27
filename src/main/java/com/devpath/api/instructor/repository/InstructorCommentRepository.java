package com.devpath.api.instructor.repository;

import com.devpath.api.instructor.entity.InstructorComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstructorCommentRepository extends JpaRepository<InstructorComment, Long> {

    List<InstructorComment> findByPostIdAndIsDeletedFalse(Long postId);

    Optional<InstructorComment> findByIdAndIsDeletedFalse(Long id);

    long countByPostIdInAndIsDeletedFalse(List<Long> postIds);
}