package org.sopt.service;

import java.time.LocalDateTime;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.DeletePostResponse;
import org.sopt.dto.response.GetAllPostsResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.dto.response.UpdatePostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository = new PostRepository();

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        String createdAt = LocalDateTime.now().toString();
        Post post = new Post(
                postRepository.generateId(),
                request.title(),
                request.content(),
                request.author(),
                createdAt
        );

        postRepository.save(post);

        return new CreatePostResponse(post.getId());
    }

    // READ - 전체 📝 과제
    public GetAllPostsResponse getAllPosts() {
        return GetAllPostsResponse.of(postRepository.findAll());
    }

    // READ - 단건 📝 과제
    public PostResponse getPost(Long id) {
        Post post = findOrThrow(id);

        return PostResponse.from(post);
    }

    // UPDATE 📝 과제
    public UpdatePostResponse updatePost(Long id, String newTitle, String newContent) {
        Post post = findOrThrow(id);
        post.update(newTitle, newContent);

        return new UpdatePostResponse(id);
    }

    // DELETE 📝 과제
    public DeletePostResponse deletePost(Long id) {
        Post post = findOrThrow(id);
        postRepository.deleteById(post.getId());

        return new DeletePostResponse(id);
    }

    private Post findOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }
}