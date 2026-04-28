package org.sopt.post.persistence;

import org.sopt.post.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostJpaRepository extends JpaRepository<PostJpaEntity, Long> {

    Page<PostJpaEntity> findAll(Pageable pageable);

    Page<PostJpaEntity> findAllByBoardType(BoardType boardType, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN FETCH p.author WHERE p.title LIKE CONCAT('%', :keyword, '%')")
    Page<PostJpaEntity> searchByTitleWithUser(String keyword, Pageable pageable);
}
