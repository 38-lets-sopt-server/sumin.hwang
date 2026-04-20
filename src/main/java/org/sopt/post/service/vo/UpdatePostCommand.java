package org.sopt.post.service.vo;

public record UpdatePostCommand(
        String newTitle,
        String newContent
) {
}
