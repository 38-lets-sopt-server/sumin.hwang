package org.sopt.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import org.sopt.post.enums.BoardType;
import org.sopt.user.entity.User;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_type", nullable = false)
    private BoardType boardType;

    @Column(name = "is_anonymous", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isAnonymous;

    @Column(name = "is_question", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isQuestion;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Post() {}

    public Post(
            String title,
            String content,
            User author,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion,
            LocalDateTime createdAt
    ) {
        this.title = title;
        this.content = content;
        this.author = author;
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

    public User getAuthor() {
        return author;
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

    public static Post create(
            String title,
            String content,
            User author,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion,
            LocalDateTime createdAt
    ) {
        return new Post(title, content, author, boardType, isAnonymous, isQuestion, createdAt);
    }

    public void update(String title, String content, boolean isAnonymous) {
        this.title = title;
        this.content = content;
        this.isAnonymous = isAnonymous;
    }
}
