package org.sopt.exception;

import org.sopt.dto.response.ApiResponse;
import org.sopt.enums.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(BusinessException e) {
        ErrorMessage errorMessage = e.getErrorMessage();

        return ResponseEntity
                .status(errorMessage.getStatus())
                .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        return ResponseEntity
                .status(ErrorMessage.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.error(e));
    }
}
