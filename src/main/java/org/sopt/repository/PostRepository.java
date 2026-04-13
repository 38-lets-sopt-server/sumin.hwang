package org.sopt.repository;

import java.util.List;
import java.util.Optional;
import org.sopt.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository {

    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(Long id);

    void deleteById(Long id);

    Long generateId();
}
