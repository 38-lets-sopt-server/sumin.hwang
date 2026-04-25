package org.sopt.post.validator;

import org.sopt.common.exception.BusinessException;
import org.sopt.post.code.PostErrorCode;

public class PostValidator {

    public static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BusinessException(PostErrorCode.POST_TITLE_REQUIRED);
        }

        if (title.length() > 50) {
            throw new BusinessException(PostErrorCode.POST_TITLE_TOO_LONG);
        }
    }
}
