package org.sopt.post.service.vo;

import org.sopt.post.validator.PostValidator;

public record UpdatePostCommand(
        String newTitle,
        String newContent
) {

    public static UpdatePostCommand of(String newTitle, String newContent) {
        PostValidator.validateTitle(newTitle);

        return new UpdatePostCommand(newTitle, newContent);
    }
}
