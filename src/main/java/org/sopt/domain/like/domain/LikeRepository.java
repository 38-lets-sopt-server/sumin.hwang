package org.sopt.domain.like.domain;

import java.util.Map;
import java.util.Set;

public interface LikeRepository {

    Like save(Like like);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    void deleteByPostIdAndUserId(Long postId, Long userId);

    Long countByPostId(Long postId);

    Map<Long, Long> countByPostIds(Set<Long> postIds);
}
