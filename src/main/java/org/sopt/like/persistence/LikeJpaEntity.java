package org.sopt.like.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.sopt.post.persistence.PostJpaEntity;
import org.sopt.user.persistence.UserJpaEntity;

@Entity(name = "Like")
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_like_post_id_user_id", columnNames = { "post_id", "user_id" })
        },
        indexes = {
                @Index(name = "idx_like_post_id", columnList = "post_id"),
        }
)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE likes SET deleted_at = NOW() WHERE id = ?")
public class LikeJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, insertable = false, updatable = false)
    private PostJpaEntity post;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private UserJpaEntity user;

    protected LikeJpaEntity() {
    }

    public LikeJpaEntity(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public static LikeJpaEntity create(Long postId, Long userId) {
        return new LikeJpaEntity(postId, userId);
    }

    public Long getId() {
        return id;
    }
}
