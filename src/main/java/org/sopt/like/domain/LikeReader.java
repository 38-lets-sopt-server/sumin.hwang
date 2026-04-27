package org.sopt.like.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.sopt.like.persistence.LikeJpaRepository;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class LikeReader {

    private final LikeJpaRepository likeJpaRepository;

    public LikeReader(LikeJpaRepository likeJpaRepository) {
        this.likeJpaRepository = likeJpaRepository;
    }

    public boolean isLiked(Post post, User user) {
        return likeJpaRepository.existsByPostIdAndUserId(post.id(), user.id());
    }

    public Map<Long, Long> countLikes(List<Post> posts) {
        return likeJpaRepository.countByPostIds(
                posts.stream()
                        .map(Post::id)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
