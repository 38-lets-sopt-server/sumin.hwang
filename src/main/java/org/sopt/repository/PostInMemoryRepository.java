package org.sopt.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.sopt.domain.Post;

public class PostInMemoryRepository implements PostRepository {
    private final Map<Long, Post> posts = new HashMap<>();
    private Long nextId = 1L;

    public Post save(Post post) {
        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findAll(int page, int size) {
        return posts.values()
                .stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
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
}