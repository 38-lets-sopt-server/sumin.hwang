package org.sopt.post.service;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.post.entity.Post;
import org.sopt.post.enums.BoardType;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.vo.CreatePostCommand;
import org.sopt.post.service.vo.PaginationCommand;
import org.sopt.post.service.vo.UpdatePostCommand;
import org.sopt.user.entity.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createPost(CreatePostCommand command) {
        User user = userRepository.findById(command.authorId())
                .orElse(null);

        Post post = Post.create(
                command.title(),
                command.content(),
                user,
                command.boardType(),
                command.isAnonymous(),
                command.isQuestion()
        );

        postRepository.save(post);
    }

    public List<Post> getAllPosts(PaginationCommand pagination, BoardType boardType) {
        int page = pagination.page();
        int size = pagination.size();
        PageRequest pageable = PageRequest.of(page, size);

        Page<Post> posts = (boardType != null) ?
                postRepository.findAllByBoardType(boardType, pageable)
                : postRepository.findAll(pageable);

        return posts.toList();
    }

    public Post findOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public void updatePost(Long id, UpdatePostCommand command) {
        Post post = findOrThrow(id);
        post.update(command.newTitle(), command.newContent(), command.isAnonymous());
    }

    public void deletePost(Long id) {
        Post post = findOrThrow(id);
        postRepository.delete(post);
    }
}