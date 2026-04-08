package org.sopt.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.sopt.domain.Post;

public class PostRepository {
    private final Map<Long, Post> posts = new HashMap<>();
    private Long nextId = 1L;

    public Post save(Post post) {
        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findAll() {
        return posts.values()
                .stream()
                .toList();
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public boolean deleteById(Long id) {
        return posts.remove(id) != null;
    }

    public Long generateId() {
        return nextId++;
    }
}