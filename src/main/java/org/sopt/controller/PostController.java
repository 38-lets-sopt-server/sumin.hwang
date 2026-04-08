package org.sopt.controller;

import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CommonResponse;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.DeletePostResponse;
import org.sopt.dto.response.GetAllPostsResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.dto.response.UpdatePostResponse;
import org.sopt.enums.SuccessMessage;
import org.sopt.service.PostService;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts 
    public CommonResponse<CreatePostResponse> createPost(CreatePostRequest request) {
        return CommonResponse.success(SuccessMessage.POST_CREATED, postService.createPost(request));
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
    public CommonResponse<UpdatePostResponse> updatePost(UpdatePostRequest request) {
        return CommonResponse.success(SuccessMessage.POST_UPDATED,
                postService.updatePost(request.id(), request.newTitle(), request.newContent()));
    }

    // DELETE /posts/{id} 📝 과제
    public CommonResponse<DeletePostResponse> deletePost(Long id) {
        return CommonResponse.success(SuccessMessage.POST_DELETED, postService.deletePost(id));
    }
}