package org.sopt.controller.dto.response;

import org.sopt.enums.ErrorMessage;
import org.sopt.enums.SuccessMessage;

public record ApiResponse<T>(
        int status,
        String code,
        String message,
        T data
) {

    private static final String SUCCESS_CODE = "SUCCESS";
    private static final int SUCCESS_STATUS = 200;

    public static <T> ApiResponse<T> success(SuccessMessage successMessage, T data) {
        return new ApiResponse<>(SUCCESS_STATUS, SUCCESS_CODE, successMessage.getClientMessage(), data);
    }

    public static ApiResponse<Void> success(SuccessMessage successMessage) {
        return success(successMessage, null);
    }

    public static ApiResponse<Void> error(ErrorMessage errorMessage) {
        return new ApiResponse<>(errorMessage.getStatus().value(), errorMessage.name(), errorMessage.getClientMessage(), null);
    }

    public static ApiResponse<Void> error(Exception e) {
        ErrorMessage errorMessage = ErrorMessage.INTERNAL_SERVER_ERROR;
        String consoleMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
        return new ApiResponse<>(errorMessage.getStatus().value(), consoleMessage, errorMessage.getClientMessage(), null);
    }
}
