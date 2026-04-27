package org.sopt.post.domain;

import org.sopt.post.persistence.PostJpaEntity;
import org.sopt.post.persistence.PostJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostDeleter {

    private final PostReader postReader;
    private final PostJpaRepository postJpaRepository;

    public PostDeleter(PostReader postReader, PostJpaRepository postJpaRepository) {
        this.postReader = postReader;
        this.postJpaRepository = postJpaRepository;
    }

    @Transactional
    public void delete(Long id) {
        Post post = postReader.read(id);
        PostJpaEntity postEntity = PostJpaEntity.from(post);

        postJpaRepository.delete(postEntity);
    }
}
