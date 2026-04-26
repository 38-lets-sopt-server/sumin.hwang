package org.sopt.post.repository;

import org.sopt.post.entity.Post;
import org.sopt.post.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByBoardType(BoardType boardType, Pageable pageable);
}
