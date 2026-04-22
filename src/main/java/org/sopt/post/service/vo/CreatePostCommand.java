package org.sopt.post.service.vo;

import org.sopt.post.enums.BoardType;
import org.sopt.post.validator.PostValidator;

public record CreatePostCommand(
        String title,
        String content,
        String author,
        BoardType boardType
) {

    public static CreatePostCommand of(String title, String content, String author, BoardType boardType) {
        PostValidator.validateTitle(title);

        return new CreatePostCommand(title, content, author, boardType);
    }
}
