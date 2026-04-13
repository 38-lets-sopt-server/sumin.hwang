package org.sopt.controller.dto.request;

import org.sopt.enums.BoardType;

// 게시글 작성 요청 (클라이언트 → 서버)
public record CreatePostRequest(
        String title,
        String content,
        String author,
        BoardType boardType
) {
}