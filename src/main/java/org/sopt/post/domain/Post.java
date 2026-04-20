package org.sopt.post.domain;

import java.time.LocalDateTime;
import org.sopt.post.enums.BoardType;

public class Post {
    private final Long id;          // 게시글 상세 화면 — 특정 게시글 식별용
    private String title;     // 목록, 상세, 글쓰기 화면 — 제목
    private String content;   // 목록(미리보기), 상세(전체) 화면 — 내용
    private final String author; // 목록, 상세 화면 — 글쓴이
    private final BoardType boardType;
    private final LocalDateTime createdAt; // 목록, 상세 화면 — 작성 시각

    public Post(Long id, String title, String content, String author, BoardType boardType, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.boardType = boardType;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static Post create(
            Long id,
            String title,
            String content,
            String author,
            BoardType boardType,
            LocalDateTime createdAt
    ) {
        return new Post(id, title, content, author, boardType, createdAt);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
