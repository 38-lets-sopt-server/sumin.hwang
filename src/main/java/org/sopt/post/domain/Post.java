package org.sopt.post.domain;

import java.time.LocalDateTime;
import org.sopt.post.enums.BoardType;

public class Post {

    private final Long id;
    private String title;
    private String content;
    private final Long authorId;
    private final BoardType boardType;
    private boolean isAnonymous;
    private final boolean isQuestion;
    private final LocalDateTime createdAt;

    private Post(
            Long id,
            String title,
            String content,
            Long authorId,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.boardType = boardType;
        this.isAnonymous = isAnonymous;
        this.isQuestion = isQuestion;
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

    public Long getAuthorId() {
        return authorId;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public boolean isQuestion() {
        return isQuestion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void update(String title, String content, boolean isAnonymous) {
        this.title = title;
        this.content = content;
        this.isAnonymous = isAnonymous;
    }

    public static Post createWithId(
            Long id,
            String title,
            String content,
            Long authorId,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion,
            LocalDateTime createdAt
    ) {
        return new Post(id, title, content, authorId, boardType, isAnonymous, isQuestion, createdAt);
    }

    public static Post create(
            String title,
            String content,
            Long authorId,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion
    ) {
        return new Post(null, title, content, authorId, boardType, isAnonymous, isQuestion, null);
    }
}
