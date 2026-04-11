package org.sopt.dto.request;

import org.sopt.validator.PostValidator;

public record UpdatePostRequest(
        Long id,
        String newTitle,
        String newContent
) {
    public static UpdatePostRequest of(Long id, String newTitle, String newContent) {
        PostValidator.validateTitle(newTitle);
        PostValidator.validateContent(newContent);

        return new UpdatePostRequest(id, newTitle, newContent);
    }
}
