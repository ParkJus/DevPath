package com.devpath.api.project.service;

import com.devpath.api.project.dto.ProjectAdvancedRequests.IdeaPostRequest;
import com.devpath.api.project.dto.ProjectAdvancedResponses.IdeaPostResponse;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.project.entity.IdeaPostStatus;
import com.devpath.domain.project.entity.ProjectIdeaPost;
import com.devpath.domain.project.repository.ProjectIdeaPostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectIdeaBoardService {

    private final ProjectIdeaPostRepository projectIdeaPostRepository;

    @Transactional
    public IdeaPostResponse createIdeaPost(IdeaPostRequest request, Long authorId) {
        ProjectIdeaPost ideaPost = ProjectIdeaPost.builder()
                .authorId(authorId)
                .title(request.getTitle())
                .content(request.getContent())
                .status(IdeaPostStatus.PUBLISHED)
                .build();

        return IdeaPostResponse.from(projectIdeaPostRepository.save(ideaPost));
    }

    public List<IdeaPostResponse> getIdeaPostList() {
        return projectIdeaPostRepository.findAllByIsDeletedFalseOrderByCreatedAtDesc().stream()
                .map(IdeaPostResponse::from)
                .toList();
    }

    public IdeaPostResponse getIdeaPostDetail(Long ideaId) {
        return IdeaPostResponse.from(getIdeaPostEntity(ideaId));
    }

    @Transactional
    public IdeaPostResponse updateIdeaPost(Long ideaId, IdeaPostRequest request, Long requesterId) {
        ProjectIdeaPost ideaPost = getIdeaPostEntity(ideaId);
        validateAuthor(ideaPost, requesterId);

        ideaPost.updateContent(request.getTitle(), request.getContent());
        return IdeaPostResponse.from(ideaPost);
    }

    @Transactional
    public void deleteIdeaPost(Long ideaId, Long requesterId) {
        ProjectIdeaPost ideaPost = getIdeaPostEntity(ideaId);
        validateAuthor(ideaPost, requesterId);
        ideaPost.markAsDeleted();
    }

    private void validateAuthor(ProjectIdeaPost ideaPost, Long requesterId) {
        if (!ideaPost.getAuthorId().equals(requesterId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION, "작성자만 게시글을 수정 또는 삭제할 수 있습니다.");
        }
    }

    private ProjectIdeaPost getIdeaPostEntity(Long ideaId) {
        return projectIdeaPostRepository.findByIdAndIsDeletedFalse(ideaId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "프로젝트 아이디어 게시글을 찾을 수 없습니다."));
    }
}
