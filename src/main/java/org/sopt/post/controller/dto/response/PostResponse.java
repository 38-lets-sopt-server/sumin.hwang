package org.sopt.post.controller.dto.response;

import java.time.LocalDateTime;
import org.sopt.post.domain.Post;
import org.sopt.post.enums.BoardType;
import org.sopt.user.domain.User;

public record PostResponse(
    Long id,
    String title,
    String content,
    AuthorInfo author,
    BoardType boardType,
    boolean isAnonymous,
    boolean isQuestion,
    long likeCount,
    LocalDateTime createdAt
) {

    public static PostResponse from(Post post, User author, long likeCount) {
        AuthorInfo authorInfo = (post.isAnonymous()) ? AuthorInfo.ANONYMOUS_INFO : AuthorInfo.from(author);

        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            authorInfo,
            post.getBoardType(),
            post.isAnonymous(),
            post.isQuestion(),
            likeCount,
            post.getCreatedAt()
        );
    }

    record AuthorInfo(
        Long authorId,
        String nickname,
        String email
    ) {

        public static AuthorInfo from(User user) {
            return new AuthorInfo(user.getId(), user.getNickname(), user.getEmail());
        }

        public static final AuthorInfo ANONYMOUS_INFO = new AuthorInfo(null, "익명", null);
    }
}
