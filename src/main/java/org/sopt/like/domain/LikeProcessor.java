package org.sopt.like.domain;

import org.sopt.common.exception.BusinessException;
import org.sopt.like.persistence.LikeJpaEntity;
import org.sopt.like.persistence.LikeJpaRepository;
import org.sopt.post.code.PostErrorCode;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class LikeProcessor {

    private final LikeReader likeReader;
    private final LikeJpaRepository likeJpaRepository;

    public LikeProcessor(LikeReader likeReader, LikeJpaRepository likeJpaRepository) {
        this.likeReader = likeReader;
        this.likeJpaRepository = likeJpaRepository;
    }

    public void like(Post post, User user) {
        if (likeReader.isLiked(post, user)) {
            throw new BusinessException(PostErrorCode.POST_ALREADY_LIKED);
        }

        LikeJpaEntity likeEntity = LikeJpaEntity.create(post.id(), user.id());

        likeJpaRepository.save(likeEntity);
    }

    public void unlike(Post post, User user) {
        if (!likeReader.isLiked(post, user)) {
            throw new BusinessException(PostErrorCode.POST_ALREADY_UNLIKED);
        }

        likeJpaRepository.deleteByPostIdAndUserId(post.id(), user.id());
    }
}
