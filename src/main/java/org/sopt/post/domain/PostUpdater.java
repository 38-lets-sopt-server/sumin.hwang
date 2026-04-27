package org.sopt.post.domain;

import org.sopt.post.persistence.PostJpaEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostUpdater {

    private final PostReader postReader;

    public PostUpdater(PostReader postReader) {
        this.postReader = postReader;
    }

    @Transactional
    public void update(Long postId, String newTitle, String newContent, boolean isAnonymous) {
        Post post = postReader.read(postId);
        PostJpaEntity postEntity = PostJpaEntity.from(post);

        postEntity.update(newTitle, newContent, isAnonymous);
    }
}
