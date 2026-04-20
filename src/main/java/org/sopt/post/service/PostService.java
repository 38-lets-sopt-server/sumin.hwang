package org.sopt.post.service;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.BoardType;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.vo.CreatePostCommand;
import org.sopt.post.service.vo.PaginationCommand;
import org.sopt.post.service.vo.UpdatePostCommand;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(CreatePostCommand command) {
        LocalDateTime createdAt = LocalDateTime.now();

        Post post = Post.create(
                postRepository.generateId(),
                command.title(),
                command.content(),
                command.author(),
                command.boardType(),
                createdAt
        );

        postRepository.save(post);
    }

    public List<Post> getAllPosts(PaginationCommand pagination, BoardType boardType) {
        int page = pagination.page();
        int size = pagination.size();

        return (boardType != null) ?
                postRepository.findAllByBoardType(boardType, page, size)
                : postRepository.findAll(page, size);
    }

    public Post findOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    public void updatePost(Long id, UpdatePostCommand command) {
        Post post = findOrThrow(id);
        post.update(command.newTitle(), command.newContent());
    }

    public void deletePost(Long id) {
        Post post = findOrThrow(id);
        postRepository.deleteById(post.getId());
    }
}