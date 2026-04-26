package org.sopt.post.controller.dto.request;

import org.sopt.post.enums.BoardType;
import org.sopt.post.service.vo.CreatePostCommand;

public record CreatePostRequest(
        String title,
        String content,
        Long authorId,
        BoardType boardType,
        boolean isAnonymous,
        boolean isQuestion
) {

    public CreatePostCommand toCommand() {
        return CreatePostCommand.of(title, content, authorId, boardType, isAnonymous, isQuestion);
    }
}