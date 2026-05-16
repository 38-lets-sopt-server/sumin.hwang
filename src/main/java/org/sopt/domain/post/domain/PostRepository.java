package org.sopt.domain.post.domain;

import java.util.Optional;
import org.sopt.common.dto.PageOffset;
import org.sopt.domain.post.enums.BoardType;
import org.springframework.data.domain.Page;

public interface PostRepository {

    Optional<Post> findById(Long id);

    Page<Post> findAll(PageOffset pageOffset);

    Page<Post> findAllByBoardType(BoardType boardType, PageOffset pageOffset);

    Page<Post> searchByTitle(String keyword, PageOffset pageOffset);

    Post save(Post post);

    void delete(Post post);
}
