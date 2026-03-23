package com.devpath.api.community.service;

import com.devpath.api.community.dto.MyPostResponse;
import com.devpath.api.community.dto.PostRequest;
import com.devpath.api.community.dto.PostResponse;
import com.devpath.api.community.dto.PostUpdateRequest;
import com.devpath.common.exception.CustomException;
import com.devpath.common.exception.ErrorCode;
import com.devpath.domain.community.entity.CommunityCategory;
import com.devpath.domain.community.entity.Post;
import com.devpath.domain.community.repository.PostRepository;
import com.devpath.domain.user.entity.User;
import com.devpath.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse createPost(Long userId, PostRequest request) {
        User user = getUser(userId);

        Post post = Post.builder()
                .user(user)
                .category(request.getCategory())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }

    public List<PostResponse> getPostsByCategory(CommunityCategory category) {
        return postRepository.findByCategoryAndIsDeletedFalseOrderByCreatedAtDesc(category)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    @Transactional
    public PostResponse getPostDetail(Long postId) {
        Post post = getActivePost(postId);

        post.incrementViewCount();

        return PostResponse.from(post);
    }

    @Transactional
    public PostResponse updatePost(Long userId, Long postId, PostUpdateRequest request) {
        Post post = getActivePost(postId);

        validatePostOwner(userId, post);

        post.updatePost(
                request.getTitle(),
                request.getContent(),
                request.getCategory()
        );

        return PostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        Post post = getActivePost(postId);

        validatePostOwner(userId, post);

        post.deletePost();
    }

    public List<MyPostResponse> getMyPosts(Long userId) {
        getUser(userId);

        return postRepository.findAllByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(MyPostResponse::from)
                .toList();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private Post getActivePost(Long postId) {
        return postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    private void validatePostOwner(Long userId, Post post) {
        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACTION);
        }
    }
}
