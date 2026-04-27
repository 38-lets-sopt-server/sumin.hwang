package org.sopt.post.domain;

import java.time.LocalDateTime;
import org.sopt.post.enums.BoardType;

public record Post(
        Long id,
        String title,
        String content,
        Long authorId,
        BoardType boardType,
        boolean isAnonymous,
        boolean isQuestion,
        LocalDateTime createdAt
) {
}
