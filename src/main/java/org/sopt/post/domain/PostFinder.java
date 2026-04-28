package org.sopt.post.domain;

import org.sopt.common.dto.PageOffset;
import org.sopt.post.enums.BoardType;
import org.sopt.post.persistence.PostJpaEntity;
import org.sopt.post.persistence.PostJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class PostFinder {

    private final PostJpaRepository postJpaRepository;

    public PostFinder(PostJpaRepository postJpaRepository) {
        this.postJpaRepository = postJpaRepository;
    }

    public Page<Post> find(BoardType boardType, PageOffset pageOffset) {
        PageRequest pageable = pageOffset.toPageRequest();

        Page<PostJpaEntity> results =
                (boardType != null) ? postJpaRepository.findAllByBoardType(boardType, pageable) :
                        postJpaRepository.findAll(pageable);

        return results.map(PostJpaEntity::toDomain);
    }

    public Page<Post> search(String keyword, PageOffset pageOffset) {
        PageRequest pageable = pageOffset.toPageRequest();

        return postJpaRepository.searchByTitleWithUser(keyword, pageable)
                .map(PostJpaEntity::toDomain);
    }
}
