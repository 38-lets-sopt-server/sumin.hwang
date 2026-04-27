package org.sopt.post.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.common.dto.PageResult;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.BoardType;

public record PostListResponse(
        List<PostItem> posts,
        long totalPages
) {
    public static PostListResponse of(PageResult<Post> posts) {
        return new PostListResponse(
                posts.contents().stream().map(PostItem::from).toList(),
                posts.totalPages()
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
            Long authorId = (post.isAnonymous()) ? null : post.authorId();

            return new PostItem(
                    post.id(),
                    post.title(),
                    post.content(),
                    authorId,
                    post.boardType(),
                    post.isAnonymous(),
                    post.isQuestion(),
                    post.createdAt()
            );
        }
    }
}
