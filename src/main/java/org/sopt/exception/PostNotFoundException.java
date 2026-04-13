package org.sopt.exception;

import org.sopt.enums.ErrorMessage;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException() {
        super(ErrorMessage.POST_NOT_FOUND);
    }
}
