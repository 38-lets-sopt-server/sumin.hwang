package org.sopt.domain.like.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<LikeJpaEntity, Long>, LikeJpaRepositoryCustom {

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    void deleteByPostIdAndUserId(Long postId, Long userId);

    Long countByPostId(Long postId);
}
