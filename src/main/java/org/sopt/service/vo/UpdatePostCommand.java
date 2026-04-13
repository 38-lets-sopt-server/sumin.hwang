package org.sopt.service.vo;

public record UpdatePostCommand(
        String newTitle,
        String newContent
) {
}
