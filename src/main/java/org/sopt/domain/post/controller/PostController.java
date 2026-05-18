package org.sopt.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.common.dto.PageOffset;
import org.sopt.domain.post.controller.dto.request.UpdatePostRequest;
import org.sopt.domain.post.controller.dto.request.CreatePostRequest;
import org.sopt.common.dto.CommonResponse;
import org.sopt.domain.post.controller.dto.response.LikeToggleResponse;
import org.sopt.domain.post.controller.dto.response.PostListResponse;
import org.sopt.domain.post.controller.dto.response.PostResponse;
import org.sopt.domain.post.enums.BoardType;
import org.sopt.domain.post.code.PostSuccessCode;
import org.sopt.domain.post.facade.PostFacade;
import org.sopt.security.provider.PrincipalProvider;
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
@RequiredArgsConstructor
public class PostController implements PostApi {

    private final PostFacade postFacade;

    @PostMapping
    public CommonResponse<Void> createPost(PrincipalProvider provider, @Valid @RequestBody CreatePostRequest request) {
        postFacade.createPost(provider, request.toCommand());
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
    public CommonResponse<Void> updatePost(PrincipalProvider provider, @PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request) {
        postFacade.updatePost(provider, postId, request.toCommand());
        return CommonResponse.success(PostSuccessCode.POST_UPDATED);
    }

    @DeleteMapping("/{postId}")
    public CommonResponse<Void> deletePost(PrincipalProvider provider, @PathVariable Long postId) {
        postFacade.deletePost(provider, postId);
        return CommonResponse.success(PostSuccessCode.POST_DELETED);
    }

    @PostMapping("/{postId}/like")
    public CommonResponse<LikeToggleResponse> toggleLike(PrincipalProvider provider, @PathVariable Long postId) {
        boolean liked = postFacade.toggleLike(provider, postId);
        PostSuccessCode code = liked ? PostSuccessCode.POST_LIKE : PostSuccessCode.POST_UNLIKE;
        return CommonResponse.success(code, new LikeToggleResponse(liked));
    }
}
