package org.sopt.dto.request;

public record UpdatePostRequest(
        String newTitle,
        String newContent
) {
}
