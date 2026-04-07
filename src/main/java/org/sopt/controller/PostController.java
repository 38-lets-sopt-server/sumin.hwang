package org.sopt.controller;

import java.util.List;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.DeletePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.dto.response.UpdatePostResponse;
import org.sopt.service.PostService;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts 
    public CreatePostResponse createPost(CreatePostRequest request) {
        try {
            return postService.createPost(request);
        } catch (IllegalArgumentException e) {
            return new CreatePostResponse(null, "🚫 " + e.getMessage());
        }
    }

    // GET /posts 📝 과제
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    // GET /posts/{id} 📝 과제
    public PostResponse getPost(Long id) {
        return postService.getPost(id);
    }

    // PUT /posts/{id} 📝 과제
    public UpdatePostResponse updatePost(UpdatePostRequest request) {
        try {
            return postService.updatePost(request.getId(), request.getNewTitle(), request.getNewContent());
        } catch (IllegalArgumentException e) {
            return new UpdatePostResponse(request.getId(), "🚫 " + e.getMessage());
        }
    }

    // DELETE /posts/{id} 📝 과제
    public DeletePostResponse deletePost(Long id) {
        try {
            return postService.deletePost(id);
        } catch (IllegalArgumentException e) {
            return new DeletePostResponse(id, "🚫 " + e.getMessage());
        }
    }
}