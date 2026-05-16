package org.sopt.domain.like.persistence;

import java.util.Map;
import java.util.Set;
import org.sopt.domain.like.domain.Like;
import org.sopt.domain.like.domain.LikeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    private final LikeJpaRepository likeJpaRepository;

    public LikeRepositoryImpl(LikeJpaRepository likeJpaRepository) {
        this.likeJpaRepository = likeJpaRepository;
    }

    @Override
    public Like save(Like like) {
        return likeJpaRepository.save(LikeJpaEntity.from(like))
                .toDomain();
    }

    @Override
    public boolean existsByPostIdAndUserId(Long postId, Long userId) {
        return likeJpaRepository.existsByPostIdAndUserId(postId, userId);
    }

    @Override
    public void deleteByPostIdAndUserId(Long postId, Long userId) {
        likeJpaRepository.deleteByPostIdAndUserId(postId, userId);
    }

    @Override
    public Long countByPostId(Long postId) {
        return likeJpaRepository.countByPostId(postId);
    }

    @Override
    public Map<Long, Long> countByPostIds(Set<Long> postIds) {
        return likeJpaRepository.countByPostIds(postIds);
    }
}
