package org.sopt.like.persistence;

import java.util.Map;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeJpaRepository extends JpaRepository<LikeJpaEntity, Long> {

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    void deleteByPostIdAndUserId(Long postId, Long postId1);

    @Query("SELECT p.id, count(l) FROM Like l JOIN FETCH l.post p GROUP BY p.id")
    Map<Long, Long> countByPostIds(Set<Long> postIds);
}
