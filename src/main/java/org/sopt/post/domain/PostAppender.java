package org.sopt.post.domain;

import org.sopt.post.persistence.PostJpaEntity;
import org.sopt.post.enums.BoardType;
import org.sopt.post.persistence.PostJpaRepository;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostAppender {

    private final PostJpaRepository postJpaRepository;

    public PostAppender(PostJpaRepository postJpaRepository) {
        this.postJpaRepository = postJpaRepository;
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
        PostJpaEntity newEntity = postJpaRepository.save(
                PostJpaEntity.create(
                        title,
                        content,
                        author.id(),
                        boardType,
                        isAnonymous,
                        isQuestion
                )
        );

        return newEntity.toDomain();
    }
}
