package org.sopt.post.code;

import org.sopt.common.code.SuccessCode;

public enum PostSuccessCode implements SuccessCode {
    POST_CREATED("게시글 작성 완료!"),
    POST_UPDATED("게시글 수정 완료!"),
    POST_DELETED("게시글 삭제 완료!"),
    POST_FOUND("게시글 조회에 성공했습니다!"),
    POST_LIKE("게시글 좋아요를 눌렀습니다!"),
    POST_UNLIKE("게시글 좋아요를 취소했습니다.");

    private final String message;

    PostSuccessCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
