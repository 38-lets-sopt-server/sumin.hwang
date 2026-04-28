package org.sopt.post.persistence;

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
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.sopt.common.entity.BaseTimeEntity;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.BoardType;
import org.sopt.user.persistence.UserJpaEntity;

@Entity(name = "Post")
@Table(name = "posts")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() WHERE id = ?")
public class PostJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false, insertable = false, updatable = false)
    private UserJpaEntity author;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_type", nullable = false)
    private BoardType boardType;

    @Column(name = "is_anonymous", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isAnonymous;

    @Column(name = "is_question", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isQuestion;

    protected PostJpaEntity() {
    }

    public PostJpaEntity(
            String title,
            String content,
            Long authorId,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion
    ) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.boardType = boardType;
        this.isAnonymous = isAnonymous;
        this.isQuestion = isQuestion;
    }

    private PostJpaEntity(
            Long id,
            String title,
            String content,
            Long authorId,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.boardType = boardType;
        this.isAnonymous = isAnonymous;
        this.isQuestion = isQuestion;
    }

    public Long getId() {
        return id;
    }

    public static PostJpaEntity create(
            String title,
            String content,
            Long authorId,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion
    ) {
        return new PostJpaEntity(title, content, authorId, boardType, isAnonymous, isQuestion);
    }

    public void update(String title, String content, boolean isAnonymous) {
        this.title = title;
        this.content = content;
        this.isAnonymous = isAnonymous;
    }

    public Post toDomain() {
        return new Post(
                id,
                title,
                content,
                authorId,
                boardType,
                isAnonymous,
                isQuestion,
                getCreatedAt()
        );
    }

    public static PostJpaEntity from(Post post) {
        return new PostJpaEntity(
                post.id(),
                post.title(),
                post.content(),
                post.authorId(),
                post.boardType(),
                post.isAnonymous(),
                post.isQuestion()
        );
    }
}
