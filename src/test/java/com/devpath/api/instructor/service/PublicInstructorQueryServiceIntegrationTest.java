package com.devpath.api.instructor.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.devpath.api.instructor.dto.InstructorPublicProfileDto;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.user.entity.User;
import com.devpath.domain.user.entity.UserProfile;
import com.devpath.domain.user.entity.UserRole;
import com.devpath.domain.user.repository.UserProfileRepository;
import com.devpath.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

// Verifies the public instructor query path against the JPA model.
@DataJpaTest(
    properties = {
      "spring.jpa.hibernate.ddl-auto=create-drop",
      "spring.sql.init.mode=never",
      "spring.jpa.defer-datasource-initialization=false"
    })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(PublicInstructorQueryService.class)
class PublicInstructorQueryServiceIntegrationTest {

  @Autowired private PublicInstructorQueryService publicInstructorQueryService;
  @Autowired private UserRepository userRepository;
  @Autowired private UserProfileRepository userProfileRepository;
  @Autowired private EntityManager entityManager;

  private Long publicInstructorId;
  private Long fallbackInstructorId;
  private Long privateInstructorId;
  private Long publicLearnerId;

  @BeforeEach
  void setUp() {
    User publicInstructor =
        userRepository.save(
            User.builder()
                .email("public-instructor@devpath.com")
                .password("encoded-password")
                .name("Public Instructor")
                .role(UserRole.ROLE_INSTRUCTOR)
                .build());

    User fallbackInstructor =
        userRepository.save(
            User.builder()
                .email("fallback-instructor@devpath.com")
                .password("encoded-password")
                .name("Fallback Instructor")
                .role(UserRole.ROLE_INSTRUCTOR)
                .build());

    User privateInstructor =
        userRepository.save(
            User.builder()
                .email("private-instructor@devpath.com")
                .password("encoded-password")
                .name("Private Instructor")
                .role(UserRole.ROLE_INSTRUCTOR)
                .build());

    User publicLearner =
        userRepository.save(
            User.builder()
                .email("public-learner@devpath.com")
                .password("encoded-password")
                .name("Public Learner")
                .role(UserRole.ROLE_LEARNER)
                .build());

    publicInstructorId = publicInstructor.getId();
    fallbackInstructorId = fallbackInstructor.getId();
    privateInstructorId = privateInstructor.getId();
    publicLearnerId = publicLearner.getId();

    userProfileRepository.save(
        UserProfile.builder()
            .user(publicInstructor)
            .profileImage("https://cdn.devpath.com/profile/public-instructor.png")
            .channelName("Public Backend Lab")
            .bio("Public Spring instructor profile")
            .githubUrl("https://github.com/public-instructor")
            .blogUrl("https://blog.devpath.com/public-instructor")
            .isPublic(true)
            .build());

    userProfileRepository.save(
        UserProfile.builder()
            .user(fallbackInstructor)
            .profileImage("https://cdn.devpath.com/profile/fallback-instructor.png")
            .channelName(" ")
            .bio("Falls back to the user name when channel name is blank")
            .githubUrl("https://github.com/fallback-instructor")
            .blogUrl("https://blog.devpath.com/fallback-instructor")
            .isPublic(true)
            .build());

    userProfileRepository.save(
        UserProfile.builder()
            .user(privateInstructor)
            .profileImage("https://cdn.devpath.com/profile/private-instructor.png")
            .channelName("Private Backend Lab")
            .bio("This profile is hidden from the public API")
            .githubUrl("https://github.com/private-instructor")
            .blogUrl("https://blog.devpath.com/private-instructor")
            .isPublic(false)
            .build());

    userProfileRepository.save(
        UserProfile.builder()
            .user(publicLearner)
            .profileImage("https://cdn.devpath.com/profile/public-learner.png")
            .channelName("Public Learner Channel")
            .bio("Learners must not be returned by the public instructor API")
            .githubUrl("https://github.com/public-learner")
            .blogUrl("https://blog.devpath.com/public-learner")
            .isPublic(true)
            .build());

    flushAndClear();
  }

  @Test
  @DisplayName("공개된 강사 프로필을 조회한다")
  void getPublicProfileReturnsInstructorSummary() {
    InstructorPublicProfileDto.ProfileResponse response =
        publicInstructorQueryService.getPublicProfile(publicInstructorId);

    assertThat(response.getInstructorId()).isEqualTo(publicInstructorId);
    assertThat(response.getNickname()).isEqualTo("Public Backend Lab");
    assertThat(response.getProfileImageUrl())
        .isEqualTo("https://cdn.devpath.com/profile/public-instructor.png");
    assertThat(response.getHeadline()).isEqualTo("Public Spring instructor profile");
    assertThat(response.getIsPublic()).isTrue();
  }

  @Test
  @DisplayName("채널명이 비어 있으면 회원 이름을 표시명으로 사용한다")
  void getPublicProfileFallsBackToUserName() {
    InstructorPublicProfileDto.ProfileResponse response =
        publicInstructorQueryService.getPublicProfile(fallbackInstructorId);

    assertThat(response.getInstructorId()).isEqualTo(fallbackInstructorId);
    assertThat(response.getNickname()).isEqualTo("Fallback Instructor");
  }

  @Test
  @DisplayName("비공개 프로필과 비강사 계정은 공개 API에서 조회할 수 없다")
  void getPublicProfileRejectsHiddenOrNonInstructorAccounts() {
    assertThatThrownBy(() -> publicInstructorQueryService.getPublicProfile(privateInstructorId))
        .isInstanceOf(CustomException.class)
        .extracting("errorCode")
        .isEqualTo(ErrorCode.RESOURCE_NOT_FOUND);

    assertThatThrownBy(() -> publicInstructorQueryService.getPublicProfile(publicLearnerId))
        .isInstanceOf(CustomException.class)
        .extracting("errorCode")
        .isEqualTo(ErrorCode.RESOURCE_NOT_FOUND);
  }

  private void flushAndClear() {
    entityManager.flush();
    entityManager.clear();
  }
}
