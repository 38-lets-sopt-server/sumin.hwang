package org.sopt.common.dto;

import org.sopt.common.code.ErrorCode;
import org.sopt.common.code.GlobalErrorCode;
import org.sopt.common.code.SuccessCode;

public record ApiResponse<T>(
        int status,
        String code,
        String message,
        T data
) {

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(200, successCode.name(), successCode.getMessage(), data);
    }

    public static ApiResponse<Void> success(SuccessCode successCode) {
        return success(successCode, null);
    }

    public static ApiResponse<Void> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getStatus().value(), errorCode.name(), errorCode.getMessage(), null);
    }

    public static ApiResponse<Void> error(Exception e) {
        GlobalErrorCode errorMessage = GlobalErrorCode.INTERNAL_SERVER_ERROR;
        String consoleMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
        return new ApiResponse<>(errorMessage.getStatus().value(), consoleMessage, errorMessage.getMessage(), null);
    }
}
