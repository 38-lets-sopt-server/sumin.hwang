package org.sopt.domain.like.persistence;

import java.util.Map;
import java.util.Set;

public interface LikeJpaRepositoryCustom {

    Map<Long, Long> countByPostIds(Set<Long> postIds);
}
