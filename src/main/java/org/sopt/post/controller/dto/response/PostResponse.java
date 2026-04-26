package org.sopt.post.controller.dto.response;

import java.time.LocalDateTime;
import org.sopt.post.entity.Post;
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
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getId(),
                post.getBoardType(),
                post.isAnonymous(),
                post.isQuestion(),
                post.getCreatedAt()
        );
    }
}