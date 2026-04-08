package org.sopt.dto.request;

public record UpdatePostRequest(
        Long id,
        String newTitle,
        String newContent
) {
}
