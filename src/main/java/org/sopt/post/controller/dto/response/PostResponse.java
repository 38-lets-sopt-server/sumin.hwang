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
        Long authorId = (post.isAnonymous()) ? null : post.getAuthor().getId();

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                authorId,
                post.getBoardType(),
                post.isAnonymous(),
                post.isQuestion(),
                post.getCreatedAt()
        );
    }
}