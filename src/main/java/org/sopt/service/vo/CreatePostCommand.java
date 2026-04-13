package org.sopt.service.vo;

import org.sopt.enums.BoardType;

public record CreatePostCommand(
        String title,
        String content,
        String author,
        BoardType boardType
) {
}
