package org.sopt.post.domain;

import org.sopt.common.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;
import org.sopt.post.persistence.PostJpaEntity;
import org.sopt.post.persistence.PostJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class PostReader {

    private final PostJpaRepository postJpaRepository;

    public PostReader(PostJpaRepository postJpaRepository) {
        this.postJpaRepository = postJpaRepository;
    }

    public Post read(Long id) {
        PostJpaEntity entity = postJpaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PostErrorCode.POST_NOT_FOUND));

        return entity.toDomain();
    }
}
