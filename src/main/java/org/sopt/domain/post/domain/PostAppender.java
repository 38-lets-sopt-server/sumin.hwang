package org.sopt.domain.post.domain;

import org.sopt.domain.post.enums.BoardType;
import org.sopt.domain.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostAppender {

    private final PostRepository postRepository;

    public PostAppender(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post append(
            String title,
            String content,
            User author,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion
    ) {
        Post post = Post.create(
                title,
                content,
                author.getId(),
                boardType,
                isAnonymous,
                isQuestion
        );

        return postRepository.save(post);
    }
}