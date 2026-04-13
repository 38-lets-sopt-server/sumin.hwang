package org.sopt.controller;

import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.GetAllPostsResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.enums.SuccessMessage;
import org.sopt.service.PostService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        postService.createPost(request);
        return ApiResponse.success(SuccessMessage.POST_CREATED);
    }

    @GetMapping
    public ApiResponse<GetAllPostsResponse> getAllPosts() {
        return ApiResponse.success(SuccessMessage.POST_FOUND, postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long postId) {
        return ApiResponse.success(SuccessMessage.POST_FOUND, postService.getPost(postId));
    }

    @PutMapping("/{postId}")
    public ApiResponse<Void> updatePost(@PathVariable Long postId, UpdatePostRequest request) {
        postService.updatePost(postId, request.newTitle(), request.newContent());
        return ApiResponse.success(SuccessMessage.POST_UPDATED);
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.success(SuccessMessage.POST_DELETED);
    }
}