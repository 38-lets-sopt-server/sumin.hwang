package org.sopt.validator;

import org.sopt.enums.ErrorMessage;
import org.sopt.exception.BusinessException;

public class PostValidator {

    private static final int MAX_TITLE_LENGTH = 50;

    public static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BusinessException(ErrorMessage.POST_TITLE_REQUIRED);
        }

        if (title.length() > MAX_TITLE_LENGTH) {
            throw new BusinessException(ErrorMessage.POST_TITLE_TOO_LONG);
        }
    }

    public static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new BusinessException(ErrorMessage.POST_CONTENT_REQUIRED);
        }
    }
}
