package org.sopt.service;

import java.time.LocalDateTime;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.GetAllPostsResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository = new PostRepository();

    // CREATE
    public void createPost(CreatePostRequest request) {
        String createdAt = LocalDateTime.now().toString();
        Post post = new Post(
                postRepository.generateId(),
                request.title(),
                request.content(),
                request.author(),
                createdAt
        );

        postRepository.save(post);
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
    public void updatePost(Long id, String newTitle, String newContent) {
        Post post = findOrThrow(id);
        post.update(newTitle, newContent);
    }

    // DELETE 📝 과제
    public void deletePost(Long id) {
        Post post = findOrThrow(id);
        postRepository.deleteById(post.getId());
    }

    private Post findOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }
}