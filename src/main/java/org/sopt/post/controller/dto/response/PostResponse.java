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
        long likeCount,
        LocalDateTime createdAt
) {

    public static PostResponse from(Post post, long likeCount) {
        Long authorId = (post.isAnonymous()) ? null : post.getAuthorId();

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                authorId,
                post.getBoardType(),
                post.isAnonymous(),
                post.isQuestion(),
                likeCount,
                post.getCreatedAt()
        );
    }
}
