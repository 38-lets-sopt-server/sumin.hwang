package org.sopt.dto.response;

import org.sopt.enums.ErrorMessage;
import org.sopt.enums.SuccessMessage;

public record CommonResponse<T>(
        boolean isSuccess,
        String errorMessage,
        String clientMessage,
        T data
) {
    public static <T> CommonResponse<T> success(SuccessMessage successMessage, T data) {
        return new CommonResponse<>(true, null, successMessage.getClientMessage(), data);
    }

    public static CommonResponse<Void> success(SuccessMessage successMessage) {
        return success(successMessage, null);
    }

    public static CommonResponse<Void> error(ErrorMessage errorMessage) {
        return new CommonResponse<>(false, errorMessage.name(), errorMessage.getClientMessage(), null);
    }

    public static CommonResponse<Void> error(Exception e) {
        ErrorMessage errorMessage = ErrorMessage.INTERNAL_SERVER_ERROR;
        String consoleMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
        return new CommonResponse<>(false, consoleMessage, errorMessage.getClientMessage(), null);
    }
}
