package org.sopt.post.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostJpaRepositoryCustom {

    Page<PostJpaEntity> searchByTitleWithUser(String keyword, Pageable pageable);
}
