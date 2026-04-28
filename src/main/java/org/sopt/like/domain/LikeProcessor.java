package org.sopt.like.domain;

import org.sopt.like.persistence.LikeJpaEntity;
import org.sopt.like.persistence.LikeJpaRepository;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LikeProcessor {

    private final LikeReader likeReader;
    private final LikeJpaRepository likeJpaRepository;

    public LikeProcessor(LikeReader likeReader, LikeJpaRepository likeJpaRepository) {
        this.likeReader = likeReader;
        this.likeJpaRepository = likeJpaRepository;
    }

    @Transactional
    public boolean toggleLike(Post post, User user) {
        if (likeReader.isLiked(post, user)) {
            likeJpaRepository.deleteByPostIdAndUserId(post.id(), user.id());
            return false;
        }

        LikeJpaEntity likeEntity = LikeJpaEntity.create(post.id(), user.id());
        likeJpaRepository.save(likeEntity);
        return true;
    }
}