package org.sopt.like.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class LikeReader {

    private final LikeRepository likeRepository;

    public LikeReader(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public boolean isLiked(Post post, User user) {
        return likeRepository.existsByPostIdAndUserId(post.getId(), user.getId());
    }

    public Map<Long, Long> countLikes(List<Post> posts) {
        Set<Long> postIds = posts.stream()
                .map(Post::getId)
                .collect(Collectors.toUnmodifiableSet());

        return likeRepository.countByPostIds(postIds);
    }

    public Long countLike(Post post) {
        return likeRepository.countByPostId(post.getId());
    }
}
