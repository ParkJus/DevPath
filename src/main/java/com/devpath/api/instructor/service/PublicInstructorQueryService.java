package com.devpath.api.instructor.service;

import com.devpath.api.instructor.dto.InstructorPublicProfileDto;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.user.entity.UserProfile;
import com.devpath.domain.user.entity.UserRole;
import com.devpath.domain.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Handles read-only public instructor profile queries.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicInstructorQueryService {

  private final UserProfileRepository userProfileRepository;

  public InstructorPublicProfileDto.ProfileResponse getPublicProfile(Long instructorId) {
    UserProfile userProfile =
        userProfileRepository
            .findPublicInstructorProfileByUserId(instructorId, UserRole.ROLE_INSTRUCTOR)
            .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

    return InstructorPublicProfileDto.ProfileResponse.builder()
        .instructorId(userProfile.getUser().getId())
        .nickname(resolveNickname(userProfile))
        .profileImageUrl(userProfile.getProfileImage())
        .headline(userProfile.getBio())
        .isPublic(userProfile.getIsPublic())
        .build();
  }

  // Falls back to the account name when the instructor has not set a channel name.
  private String resolveNickname(UserProfile userProfile) {
    String channelName = userProfile.getChannelName();

    if (channelName != null && !channelName.isBlank()) {
      return channelName;
    }

    return userProfile.getUser().getName();
  }
}
