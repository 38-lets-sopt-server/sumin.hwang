package org.sopt.domain.post.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostDeleter {

    private final PostReader postReader;
    private final PostRepository postRepository;

    public PostDeleter(PostReader postReader, PostRepository postRepository) {
        this.postReader = postReader;
        this.postRepository = postRepository;
    }

    @Transactional
    public void delete(Long id) {
        Post post = postReader.read(id);

        postRepository.delete(post);
    }
}