package org.sopt.post.controller;

import jakarta.validation.Valid;
import org.sopt.common.dto.PageOffset;
import org.sopt.post.controller.dto.request.UpdatePostRequest;
import org.sopt.post.controller.dto.request.CreatePostRequest;
import org.sopt.common.dto.CommonResponse;
import org.sopt.post.controller.dto.response.LikeToggleResponse;
import org.sopt.post.controller.dto.response.PostListResponse;
import org.sopt.post.controller.dto.response.PostResponse;
import org.sopt.post.enums.BoardType;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.facade.PostFacade;
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

    private final PostFacade postFacade;

    public PostController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @PostMapping
    public CommonResponse<Void> createPost(@Valid @RequestBody CreatePostRequest request) {
        postFacade.createPost(request.toCommand());
        return CommonResponse.success(PostSuccessCode.POST_CREATED);
    }

    @GetMapping
    public CommonResponse<PostListResponse> getAllPosts(
        @RequestParam(required = false, defaultValue = "FREE") String boardType,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        PostListResponse response = postFacade.getAllPosts(
            PageOffset.of(page, size),
            BoardType.from(boardType.toUpperCase())
        );
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
        postFacade.updatePost(postId, request.toCommand());
        return CommonResponse.success(PostSuccessCode.POST_UPDATED);
    }

    @DeleteMapping("/{postId}")
    public CommonResponse<Void> deletePost(@PathVariable Long postId) {
        postFacade.deletePost(postId);
        return CommonResponse.success(PostSuccessCode.POST_DELETED);
    }

    @PostMapping("/{postId}/like/{userId}")
    public CommonResponse<LikeToggleResponse> toggleLike(@PathVariable Long postId, @PathVariable Long userId) {
        boolean liked = postFacade.toggleLike(postId, userId);
        PostSuccessCode code = liked ? PostSuccessCode.POST_LIKE : PostSuccessCode.POST_UNLIKE;
        return CommonResponse.success(code, new LikeToggleResponse(liked));
    }
}
