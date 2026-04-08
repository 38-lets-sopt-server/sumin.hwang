package org.sopt.validator;

import org.sopt.enums.ErrorMessage;
import org.sopt.exception.CustomException;

public class PostValidator {

    public static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new CustomException(ErrorMessage.POST_TITLE_REQUIRED);
        }
    }

    public static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new CustomException(ErrorMessage.POST_CONTENT_REQUIRED);
        }
    }
}
