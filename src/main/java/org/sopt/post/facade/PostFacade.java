package org.sopt.post.facade;

import java.util.Map;
import org.sopt.common.dto.PageOffset;
import org.sopt.common.dto.PageResult;
import org.sopt.like.service.LikeService;
import org.sopt.post.controller.dto.response.PostListResponse;
import org.sopt.post.controller.dto.response.PostResponse;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.BoardType;
import org.sopt.post.service.PostService;
import org.sopt.post.service.vo.CreatePostCommand;
import org.sopt.post.service.vo.UpdatePostCommand;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class PostFacade {

    private final PostService postService;
    private final LikeService likeService;

    public PostFacade(PostService postService, LikeService likeService) {
        this.postService = postService;
        this.likeService = likeService;
    }

    @Transactional
    public void createPost(CreatePostCommand command) {
        postService.createPost(command);
    }

    public PostListResponse getAllPosts(PageOffset pageOffset, BoardType boardType) {
        PageResult<Post> posts = postService.getAllPosts(pageOffset, boardType);
        Map<Long, Long> likeMap = likeService.countLikes(posts.contents());

        return PostListResponse.of(posts, likeMap);
    }

    public PostListResponse searchPosts(String keyword, PageOffset pageOffset) {
        PageResult<Post> posts = postService.searchPosts(keyword, pageOffset);
        Map<Long, Long> likeMap = likeService.countLikes(posts.contents());

        return PostListResponse.of(posts, likeMap);
    }

    public PostResponse getPost(Long postId) {
        Post post = postService.getPostById(postId);
        long likeCount = likeService.countLike(postId);

        return PostResponse.from(post, likeCount);
    }

    @Transactional
    public void updatePost(Long postId, UpdatePostCommand command) {
        postService.updatePost(postId, command);
    }

    @Transactional
    public void deletePost(Long postId) {
        postService.deletePost(postId);
    }

    @Transactional
    public boolean toggleLike(Long postId, Long userId) {
        return likeService.toggleLike(postId, userId);
    }
}
