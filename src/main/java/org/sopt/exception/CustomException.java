package org.sopt.exception;

import org.sopt.enums.ErrorMessage;

public class CustomException extends RuntimeException {

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getClientMessage());
    }
}
