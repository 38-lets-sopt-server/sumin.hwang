package org.sopt.controller.dto.request;

public record UpdatePostRequest(
        String newTitle,
        String newContent
) {
}
