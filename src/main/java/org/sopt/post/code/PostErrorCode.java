package org.sopt.post.code;

import org.sopt.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    POST_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "게시글 제목은 필수입니다."),
    POST_TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "게시글 제목 최대 길이를 초과했습니다.");

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
