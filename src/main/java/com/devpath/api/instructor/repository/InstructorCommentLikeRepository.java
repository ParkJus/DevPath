package com.devpath.api.instructor.repository;

import com.devpath.api.instructor.entity.InstructorCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorCommentLikeRepository extends JpaRepository<InstructorCommentLike, Long> {

    Optional<InstructorCommentLike> findByCommentIdAndUserId(Long commentId, Long userId);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
}