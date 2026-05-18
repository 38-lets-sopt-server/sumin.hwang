package org.sopt.domain.like.domain;

import org.sopt.domain.post.domain.Post;
import org.sopt.domain.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LikeProcessor {

    private final LikeReader likeReader;
    private final LikeRepository likeRepository;

    public LikeProcessor(LikeReader likeReader, LikeRepository likeRepository) {
        this.likeReader = likeReader;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public boolean toggleLike(Post post, User user) {
        if (likeReader.isLiked(post, user)) {
            likeRepository.deleteByPostIdAndUserId(post.getId(), user.getId());
            return false;
        }

        likeRepository.save(Like.create(post.getId(), user.getId()));
        return true;
    }
}
