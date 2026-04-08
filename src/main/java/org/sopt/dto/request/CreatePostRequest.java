package org.sopt.dto.request;

import org.sopt.validator.PostValidator;

// 게시글 작성 요청 (클라이언트 → 서버)
public record CreatePostRequest(
        String title,
        String content,
        String author
) {

    public static CreatePostRequest of(String title, String content, String author) {
        PostValidator.validateTitle(title);
        PostValidator.validateContent(content);

        return new CreatePostRequest(title, content, author);
    }
}