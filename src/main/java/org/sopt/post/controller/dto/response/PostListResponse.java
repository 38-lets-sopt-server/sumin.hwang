package org.sopt.post.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.sopt.common.dto.PageResult;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.BoardType;
import org.sopt.user.domain.User;

public record PostListResponse(
        List<PostItem> posts,
        long totalPages
) {
    public static PostListResponse of(PageResult<Post> posts, Map<Long, User> authorMap, Map<Long, Long> likeMap) {
        return new PostListResponse(
                posts.contents()
                        .stream()
                        .map(post ->
                                PostItem.from(
                                        post,
                                        authorMap.get(post.getAuthorId()),
                                        likeMap.getOrDefault(post.getId(), 0L))
                        )
                        .toList(),
                posts.totalPages()
        );
    }

    record PostItem(
            Long id,
            String title,
            String content,
            String authorNickname,
            BoardType boardType,
            boolean isAnonymous,
            boolean isQuestion,
            long likeCount,
            LocalDateTime createdAt
    ) {
        public static PostItem from(Post post, User author, long likeCount) {
            String authorNickname = (post.isAnonymous()) ? "익명" : author.getNickname();
            return new PostItem(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    authorNickname,
                    post.getBoardType(),
                    post.isAnonymous(),
                    post.isQuestion(),
                    likeCount,
                    post.getCreatedAt()
            );
        }
    }
}
