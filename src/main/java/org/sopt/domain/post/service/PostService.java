package org.sopt.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.common.dto.PageResult;
import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.domain.PostAppender;
import org.sopt.domain.post.domain.PostAuthorValidator;
import org.sopt.domain.post.domain.PostDeleter;
import org.sopt.domain.post.domain.PostFinder;
import org.sopt.domain.post.domain.PostReader;
import org.sopt.domain.post.domain.PostUpdater;
import org.sopt.domain.post.enums.BoardType;
import org.sopt.domain.post.service.vo.CreatePostCommand;
import org.sopt.common.dto.PageOffset;
import org.sopt.domain.post.service.vo.UpdatePostCommand;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.domain.UserReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostAppender postAppender;
    private final UserReader userReader;
    private final PostFinder postFinder;
    private final PostReader postReader;
    private final PostUpdater postUpdater;
    private final PostDeleter postDeleter;
    private final PostAuthorValidator postAuthorValidator;

    @Transactional
    public void createPost(Long userId, CreatePostCommand command) {
        User author = userReader.read(userId);

        postAppender.append(
                command.title(),
                command.content(),
                author,
                command.boardType(),
                command.isAnonymous(),
                command.isQuestion()
        );
    }

    public PageResult<Post> getAllPosts(PageOffset pageOffset, BoardType boardType) {
        return PageResult.from(postFinder.find(boardType, pageOffset));
    }

    public PageResult<Post> searchPosts(String keyword, PageOffset pageOffset) {
        return PageResult.from(postFinder.search(keyword, pageOffset));
    }

    public Post getPostById(Long id) {
        return postReader.read(id);
    }

    @Transactional
    public void updatePost(Long userId, Long id, UpdatePostCommand command) {
        postAuthorValidator.validate(id, userId);
        postUpdater.update(id, command.newTitle(), command.newContent(), command.isAnonymous());
    }

    @Transactional
    public void deletePost(Long userId, Long id) {
        postAuthorValidator.validate(id, userId);
        postDeleter.delete(id);
    }
}