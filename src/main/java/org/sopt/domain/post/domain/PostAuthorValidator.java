package org.sopt.domain.post.domain;

import lombok.RequiredArgsConstructor;
import org.sopt.common.exception.BusinessException;
import org.sopt.domain.post.code.PostErrorCode;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.domain.UserReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostAuthorValidator {

    private final PostReader postReader;
    private final UserReader userReader;

    public void validate(Long postId, Long userId) {
        Post post = postReader.read(postId);
        User user = userReader.read(userId);

        if (!post.getAuthorId().equals(user.getId())) {
            throw new BusinessException(PostErrorCode.POST_ACCESS_DENIED);
        }
    }
}
