package org.sopt.post.code;

import org.sopt.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    INVALID_BOARD_TYPE(HttpStatus.BAD_REQUEST, "올바르지 않은 게시판 종류입니다.");

    private final HttpStatus status;
    private final String message;

    PostErrorCode(HttpStatus status, String message) {
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
