package org.sopt.service;

import java.time.LocalDateTime;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.GetAllPostsResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.vo.PaginationCommand;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

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

    public GetAllPostsResponse getAllPosts(PaginationCommand pagination) {
        return GetAllPostsResponse.of(postRepository.findAll(pagination.page(), pagination.size()));
    }

    public PostResponse getPost(Long id) {
        Post post = findOrThrow(id);

        return PostResponse.from(post);
    }

    public void updatePost(Long id, String newTitle, String newContent) {
        Post post = findOrThrow(id);
        post.update(newTitle, newContent);
    }

    public void deletePost(Long id) {
        Post post = findOrThrow(id);
        postRepository.deleteById(post.getId());
    }

    private Post findOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }
}