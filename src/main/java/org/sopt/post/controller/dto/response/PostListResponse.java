package org.sopt.post.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.post.entity.Post;
import org.sopt.post.enums.BoardType;

public record PostListResponse(
        List<PostItem> posts
) {
    public static PostListResponse of(List<Post> posts) {
        return new PostListResponse(
                posts.stream().map(PostItem::from).toList()
        );
    }

    record PostItem(
            Long id,
            String title,
            String content,
            Long authorId,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion,
            LocalDateTime createdAt
    ) {
        public static PostItem from(Post post) {
            Long authorId = (post.isAnonymous()) ? null : post.getAuthor().getId();

            return new PostItem(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    authorId,
                    post.getBoardType(),
                    post.isAnonymous(),
                    post.isQuestion(),
                    post.getCreatedAt()
            );
        }
    }
}
