package org.sopt.domain.post.code;

import lombok.Getter;
import org.sopt.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum PostErrorCode implements ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    INVALID_BOARD_TYPE(HttpStatus.BAD_REQUEST, "올바르지 않은 게시판 종류입니다."),
    POST_ACCESS_DENIED(HttpStatus.FORBIDDEN, "게시글의 접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    PostErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
