package org.sopt.controller.dto.request;

import org.sopt.service.vo.UpdatePostCommand;

public record UpdatePostRequest(
        String newTitle,
        String newContent
) {

    public UpdatePostCommand toCommand() {
        return new UpdatePostCommand(newTitle, newContent);
    }
}
