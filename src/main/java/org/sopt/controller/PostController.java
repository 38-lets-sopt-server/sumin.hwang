package org.sopt.controller;

import java.util.List;
import org.sopt.controller.dto.request.UpdatePostRequest;
import org.sopt.controller.dto.request.CreatePostRequest;
import org.sopt.controller.dto.response.ApiResponse;
import org.sopt.controller.dto.response.GetAllPostsResponse;
import org.sopt.controller.dto.response.PostResponse;
import org.sopt.domain.Post;
import org.sopt.enums.BoardType;
import org.sopt.enums.SuccessMessage;
import org.sopt.service.PostService;
import org.sopt.service.vo.PaginationCommand;
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
        return ApiResponse.success(SuccessMessage.POST_CREATED);
    }

    @GetMapping
    public ApiResponse<GetAllPostsResponse> getAllPosts(
            @RequestParam(required = false) String boardType,
            @RequestParam int page,
            @RequestParam int size
    ) {
        List<Post> posts = postService.getAllPosts(PaginationCommand.of(page, size),
                BoardType.valueOf(boardType.toUpperCase()));

        return ApiResponse.success(SuccessMessage.POST_FOUND, GetAllPostsResponse.of(posts));
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long postId) {
        Post post = postService.findOrThrow(postId);

        return ApiResponse.success(SuccessMessage.POST_FOUND, PostResponse.from(post));
    }

    @PutMapping("/{postId}")
    public ApiResponse<Void> updatePost(@PathVariable Long postId, UpdatePostRequest request) {
        postService.updatePost(postId, request.toCommand());
        return ApiResponse.success(SuccessMessage.POST_UPDATED);
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.success(SuccessMessage.POST_DELETED);
    }
}