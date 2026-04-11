package org.sopt.exception;

import org.sopt.enums.ErrorMessage;

public class CustomException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getClientMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
