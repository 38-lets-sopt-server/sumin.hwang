package org.sopt.common.code;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 다시 시도해 주세요."),
    INVALID_PAGE(HttpStatus.BAD_REQUEST, "page는 0 이상이어야 합니다."),
    INVALID_PAGE_SIZE(HttpStatus.BAD_REQUEST, "size는 양수여야 합니다.");

    private final HttpStatus status;
    private final String message;

    GlobalErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
