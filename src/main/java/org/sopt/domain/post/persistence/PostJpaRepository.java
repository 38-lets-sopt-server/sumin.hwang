package org.sopt.domain.post.persistence;

import org.sopt.domain.post.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostJpaEntity, Long>, PostJpaRepositoryCustom {

    Page<PostJpaEntity> findAll(Pageable pageable);

    Page<PostJpaEntity> findAllByBoardType(BoardType boardType, Pageable pageable);
}
