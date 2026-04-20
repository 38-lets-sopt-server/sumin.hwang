package org.sopt.post.controller.dto.request;

import org.sopt.post.enums.BoardType;
import org.sopt.post.service.vo.CreatePostCommand;

public record CreatePostRequest(
        String title,
        String content,
        String author,
        BoardType boardType
) {

    public CreatePostCommand toCommand() {
        return new CreatePostCommand(title, content, author, boardType);
    }
}