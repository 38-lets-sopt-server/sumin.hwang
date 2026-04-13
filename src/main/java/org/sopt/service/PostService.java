package org.sopt.service;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.GetAllPostsResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.enums.BoardType;
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
        LocalDateTime createdAt = LocalDateTime.now();

        Post post = Post.create(
                postRepository.generateId(),
                request.title(),
                request.content(),
                request.author(),
                request.boardType(),
                createdAt
        );

        postRepository.save(post);
    }

    public GetAllPostsResponse getAllPosts(PaginationCommand pagination, BoardType boardType) {
        int page = pagination.page();
        int size = pagination.size();

        List<Post> posts = (boardType != null) ?
                postRepository.findAllByBoardType(boardType, page, size)
                : postRepository.findAll(page, size);

        return GetAllPostsResponse.of(posts);
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