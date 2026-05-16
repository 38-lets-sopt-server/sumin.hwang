package org.sopt.domain.like.domain;

public class Like {

    private final Long id;
    private final Long postId;
    private final Long userId;

    private Like(Long id, Long postId, Long userId) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
    }

    public static Like createWithId(Long id, Long postId, Long userId) {
        return new Like(id, postId, userId);
    }

    public static Like create(Long postId, Long userId) {
        return new Like(null, postId, userId);
    }
}
