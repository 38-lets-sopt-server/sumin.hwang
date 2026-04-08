package org.sopt.controller;

import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CommonResponse;
import org.sopt.dto.response.GetAllPostsResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.enums.SuccessMessage;
import org.sopt.service.PostService;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts 
    public CommonResponse<Void> createPost(CreatePostRequest request) {
        postService.createPost(request);
        return CommonResponse.success(SuccessMessage.POST_CREATED);
    }

    // GET /posts 📝 과제
    public CommonResponse<GetAllPostsResponse> getAllPosts() {
        return CommonResponse.success(SuccessMessage.POST_FOUND, postService.getAllPosts());
    }

    // GET /posts/{id} 📝 과제
    public CommonResponse<PostResponse> getPost(Long id) {
        return CommonResponse.success(SuccessMessage.POST_FOUND, postService.getPost(id));
    }

    // PUT /posts/{id} 📝 과제
    public CommonResponse<Void> updatePost(UpdatePostRequest request) {
        postService.updatePost(request.id(), request.newTitle(), request.newContent());
        return CommonResponse.success(SuccessMessage.POST_UPDATED);
    }

    // DELETE /posts/{id} 📝 과제
    public CommonResponse<Void> deletePost(Long id) {
        postService.deletePost(id);
        return CommonResponse.success(SuccessMessage.POST_DELETED);
    }
}