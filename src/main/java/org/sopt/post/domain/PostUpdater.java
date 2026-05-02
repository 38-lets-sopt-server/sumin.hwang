package org.sopt.post.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostUpdater {

    private final PostReader postReader;
    private final PostRepository postRepository;

    public PostUpdater(PostReader postReader, PostRepository postRepository) {
        this.postReader = postReader;
        this.postRepository = postRepository;
    }

    @Transactional
    public void update(Long postId, String newTitle, String newContent, boolean isAnonymous) {
        Post post = postReader.read(postId);

        post.update(newTitle, newContent, isAnonymous);
        postRepository.save(post);
    }
}
