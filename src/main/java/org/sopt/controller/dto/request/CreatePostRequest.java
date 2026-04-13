package org.sopt.controller.dto.request;

import org.sopt.enums.BoardType;
import org.sopt.service.vo.CreatePostCommand;

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