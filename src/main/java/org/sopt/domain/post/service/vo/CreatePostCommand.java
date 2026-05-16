package org.sopt.domain.post.service.vo;

import org.sopt.domain.post.enums.BoardType;

public record CreatePostCommand(
        String title,
        String content,
        Long authorId,
        BoardType boardType,
        boolean isAnonymous,
        boolean isQuestion
) {

    public static CreatePostCommand of(
            String title,
            String content,
            Long authorId,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion
    ) {

        return new CreatePostCommand(title, content, authorId, boardType, isAnonymous, isQuestion);
    }
}
