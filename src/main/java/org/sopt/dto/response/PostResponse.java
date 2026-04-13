package org.sopt.dto.response;

import java.time.LocalDateTime;
import org.sopt.domain.Post;

public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        LocalDateTime createdAt
) {

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getCreatedAt()
        );
    }
}