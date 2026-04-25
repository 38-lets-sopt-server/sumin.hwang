package org.sopt.post.repository;

import java.util.List;
import java.util.Optional;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.BoardType;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository {

    Post save(Post post);

    List<Post> findAll(int page, int size);

    Optional<Post> findById(Long id);

    void deleteById(Long id);

    Long generateId();

    List<Post> findAllByBoardType(BoardType boardType, int page, int size);
}
