package org.sopt.post.service.vo;

import org.sopt.post.enums.BoardType;

public record CreatePostCommand(
        String title,
        String content,
        String author,
        BoardType boardType
) {
}
