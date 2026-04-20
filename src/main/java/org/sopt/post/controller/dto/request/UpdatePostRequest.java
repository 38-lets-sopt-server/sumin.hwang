package org.sopt.post.controller.dto.request;

import org.sopt.post.service.vo.UpdatePostCommand;

public record UpdatePostRequest(
        String newTitle,
        String newContent
) {

    public UpdatePostCommand toCommand() {
        return new UpdatePostCommand(newTitle, newContent);
    }
}
