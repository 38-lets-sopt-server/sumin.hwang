package org.sopt.post.controller.dto.response;

import java.time.LocalDateTime;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.BoardType;

public record PostResponse(
        Long id,
        String title,
        String content,
        Long authorId,
        BoardType boardType,
        boolean isAnonymous,
        boolean isQuestion,
        LocalDateTime createdAt
) {

    public static PostResponse from(Post post) {
        Long authorId = (post.isAnonymous()) ? null : post.authorId();

        return new PostResponse(
                post.id(),
                post.title(),
                post.content(),
                authorId,
                post.boardType(),
                post.isAnonymous(),
                post.isQuestion(),
                post.createdAt()
        );
    }
}