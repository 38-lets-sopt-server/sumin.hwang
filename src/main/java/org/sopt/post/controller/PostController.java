package org.sopt.post.controller;

import jakarta.validation.Valid;
import org.sopt.common.dto.PageOffset;
import org.sopt.like.service.LikeService;
import org.sopt.post.controller.dto.request.UpdatePostRequest;
import org.sopt.post.controller.dto.request.CreatePostRequest;
import org.sopt.common.dto.CommonResponse;
import org.sopt.post.controller.dto.response.PostListResponse;
import org.sopt.post.controller.dto.response.PostResponse;
import org.sopt.post.enums.BoardType;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.facade.PostFacade;
import org.sopt.post.service.PostService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController implements PostApi {

    private final PostService postService;
    private final LikeService likeService;
    private final PostFacade postFacade;

    public PostController(PostService postService, LikeService likeService, PostFacade postFacade) {
        this.postService = postService;
        this.likeService = likeService;
        this.postFacade = postFacade;
    }

    @PostMapping
    public CommonResponse<Void> createPost(@Valid @RequestBody CreatePostRequest request) {
        postService.createPost(request.toCommand());
        return CommonResponse.success(PostSuccessCode.POST_CREATED);
    }

    @GetMapping
    public CommonResponse<PostListResponse> getAllPosts(
            @RequestParam(required = false, defaultValue = "FREE") String boardType,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        PostListResponse response = postFacade.getAllPosts(PageOffset.of(page, size), BoardType.valueOf(boardType.toUpperCase()));
        return CommonResponse.success(PostSuccessCode.POST_FOUND, response);
    }

    @GetMapping("/search")
    public CommonResponse<PostListResponse> searchPosts(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        PostListResponse response = postFacade.searchPosts(keyword, PageOffset.of(page, size));
        return CommonResponse.success(PostSuccessCode.POST_FOUND, response);
    }

    @GetMapping("/{postId}")
    public CommonResponse<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse response = postFacade.getPost(postId);
        return CommonResponse.success(PostSuccessCode.POST_FOUND, response);
    }

    @PutMapping("/{postId}")
    public CommonResponse<Void> updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request) {
        postService.updatePost(postId, request.toCommand());
        return CommonResponse.success(PostSuccessCode.POST_UPDATED);
    }

    @DeleteMapping("/{postId}")
    public CommonResponse<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return CommonResponse.success(PostSuccessCode.POST_DELETED);
    }

    @PostMapping("/{postId}/like/{userId}")
    public CommonResponse<Void> like(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.like(postId, userId);
        return CommonResponse.success(PostSuccessCode.POST_LIKE);
    }

    @PostMapping("/{postId}/unlike/{userId}")
    public CommonResponse<Void> unlike(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.unlike(postId, userId);
        return CommonResponse.success(PostSuccessCode.POST_UNLIKE);
    }
}
