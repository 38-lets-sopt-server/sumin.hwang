package org.sopt.domain.post.controller.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import org.sopt.common.validation.ValidationMessage;
import org.sopt.domain.post.service.vo.UpdatePostCommand;

public record UpdatePostRequest(
        @NotBlank(message = ValidationMessage.TITLE_REQUIRED)
        @Max(value = 50, message = ValidationMessage.TITLE_TOO_LONG)
        String newTitle,
        String newContent,
        boolean isAnonymous
) {

    public UpdatePostCommand toCommand() {
        return UpdatePostCommand.of(newTitle, newContent, isAnonymous);
    }
}
