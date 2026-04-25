package org.sopt.common.code;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    POST_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "게시글 제목은 필수입니다."),
    POST_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "게시글 내용은 필수입니다."),
    POST_TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "게시글 제목 최대 길이를 초과했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 다시 시도해 주세요.");

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
