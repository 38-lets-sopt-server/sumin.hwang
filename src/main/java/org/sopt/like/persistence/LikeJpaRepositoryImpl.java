package org.sopt.like.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LikeJpaRepositoryImpl implements LikeJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LikeJpaRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Map<Long, Long> countByPostIds(Set<Long> postIds) {
        if (postIds.isEmpty()) {
            return Map.of();
        }

        QLikeJpaEntity like = QLikeJpaEntity.likeJpaEntity;

        return queryFactory
                .select(like.postId, like.count())
                .from(like)
                .where(like.postId.in(postIds))
                .groupBy(like.postId)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(like.postId),
                        tuple -> tuple.get(like.count())
                ));
    }
}
