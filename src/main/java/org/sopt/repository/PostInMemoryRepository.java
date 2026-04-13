package org.sopt.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.sopt.domain.Post;
import org.sopt.enums.BoardType;
import org.springframework.stereotype.Repository;

@Repository
public class PostInMemoryRepository implements PostRepository {
    private final Map<Long, Post> posts = new HashMap<>();
    private Long nextId = 1L;

    public Post save(Post post) {
        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findAll(int page, int size) {
        return paginate(posts.values().stream(), page, size);
    }

    @Override
    public List<Post> findAllByBoardType(BoardType boardType, int page, int size) {
        return paginate(
                posts.values()
                        .stream()
                        .filter(p -> p.getBoardType().equals(boardType)),
                page,
                size
        );
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public void deleteById(Long id) {
        posts.remove(id);
    }

    public Long generateId() {
        return nextId++;
    }

    private List<Post> paginate(Stream<Post> stream, int page, int size) {
        return stream
                .skip((long) page * size)
                .limit(size)
                .toList();
    }
}