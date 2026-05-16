package org.sopt.domain.post.controller.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import org.sopt.common.validation.ValidationMessage;
import org.sopt.domain.post.enums.BoardType;
import org.sopt.domain.post.service.vo.CreatePostCommand;

public record CreatePostRequest(
        @NotBlank(message = ValidationMessage.TITLE_REQUIRED)
        @Max(value = 50, message = ValidationMessage.TITLE_TOO_LONG)
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