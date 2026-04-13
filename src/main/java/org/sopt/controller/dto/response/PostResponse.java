package org.sopt.controller.dto.response;

import java.time.LocalDateTime;
import org.sopt.domain.Post;
import org.sopt.enums.BoardType;

public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        BoardType boardType,
        LocalDateTime createdAt
) {

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getBoardType(),
                post.getCreatedAt()
        );
    }
}