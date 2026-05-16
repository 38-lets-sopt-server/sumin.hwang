package org.sopt.domain.post.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.sopt.user.persistence.QUserJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public class PostJpaRepositoryCustomImpl implements PostJpaRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QPostJpaEntity qPost = QPostJpaEntity.postJpaEntity;
    private final QUserJpaEntity qUser = QUserJpaEntity.userJpaEntity;

    public PostJpaRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<PostJpaEntity> searchByTitle(String keyword, Pageable pageable) {
        List<PostJpaEntity> queryResults = queryFactory
                .selectFrom(qPost)
                .where(titleContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(qPost.count())
                .from(qPost)
                .where(titleContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();

        return PageableExecutionUtils.getPage(queryResults, pageable, () -> (count != null ? count : 0L));
    }

    private BooleanExpression titleContains(String keyword) {
        return qPost.title.containsIgnoreCase(keyword);
    }
}
