package org.sopt.common.exception;

import org.sopt.common.code.ErrorCode;
import org.sopt.common.dto.CommonResponse;
import org.sopt.common.code.GlobalErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<?>> handleCustomException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(CommonResponse.error(errorCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleException(Exception e) {
        return ResponseEntity
                .status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(CommonResponse.error(e));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse<?>> handleBindException(BindException exception) {

        return handleValidationException(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        return handleValidationException(exception);
    }

    private ResponseEntity<CommonResponse<?>> handleValidationException(BindException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("올바르지 않은 입력값입니다.");

        return ResponseEntity
                .status(status)
                .body(CommonResponse.validationError(message));
    }
}
