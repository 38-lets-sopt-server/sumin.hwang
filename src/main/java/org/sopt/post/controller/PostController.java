package org.sopt.post.controller;

import java.util.List;
import org.sopt.post.controller.dto.request.UpdatePostRequest;
import org.sopt.post.controller.dto.request.CreatePostRequest;
import org.sopt.common.dto.ApiResponse;
import org.sopt.post.controller.dto.response.GetAllPostsResponse;
import org.sopt.post.controller.dto.response.PostResponse;
import org.sopt.post.entity.Post;
import org.sopt.post.enums.BoardType;
import org.sopt.post.code.PostSuccessCode;
import org.sopt.post.service.PostService;
import org.sopt.post.service.vo.PaginationCommand;
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
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<Void> createPost(@RequestBody CreatePostRequest request) {
        postService.createPost(request.toCommand());
        return ApiResponse.success(PostSuccessCode.POST_CREATED);
    }

    @GetMapping
    public ApiResponse<GetAllPostsResponse> getAllPosts(
            @RequestParam(required = false, defaultValue = "FREE") String boardType,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<Post> posts = postService.getAllPosts(
                PaginationCommand.of(page, size),
                BoardType.valueOf(boardType.toUpperCase())
        );

        return ApiResponse.success(PostSuccessCode.POST_FOUND, GetAllPostsResponse.of(posts));
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long postId) {
        Post post = postService.findOrThrow(postId);

        return ApiResponse.success(PostSuccessCode.POST_FOUND, PostResponse.from(post));
    }

    @PutMapping("/{postId}")
    public ApiResponse<Void> updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequest request) {
        postService.updatePost(postId, request.toCommand());
        return ApiResponse.success(PostSuccessCode.POST_UPDATED);
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.success(PostSuccessCode.POST_DELETED);
    }
}