package org.sopt.post.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostJpaRepositoryCustom {

    Page<PostJpaEntity> searchByTitle(String keyword, Pageable pageable);
}
