package org.sopt.post.domain;

import org.sopt.common.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PostReader {

    private final PostRepository postRepository;

    public PostReader(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post read(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PostErrorCode.POST_NOT_FOUND));
    }
}
