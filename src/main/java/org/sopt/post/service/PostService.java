package org.sopt.post.service;

import org.sopt.common.dto.PageResult;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostAppender;
import org.sopt.post.domain.PostDeleter;
import org.sopt.post.domain.PostFinder;
import org.sopt.post.domain.PostReader;
import org.sopt.post.domain.PostUpdater;
import org.sopt.post.enums.BoardType;
import org.sopt.post.service.vo.CreatePostCommand;
import org.sopt.common.dto.PageOffset;
import org.sopt.post.service.vo.UpdatePostCommand;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostAppender postAppender;
    private final UserReader userReader;
    private final PostFinder postFinder;
    private final PostReader postReader;
    private final PostUpdater postUpdater;
    private final PostDeleter postDeleter;

    public PostService(
            PostAppender postAppender,
            UserReader userReader,
            PostFinder postFinder,
            PostReader postReader,
            PostUpdater postUpdater,
            PostDeleter postDeleter
    ) {
        this.postAppender = postAppender;
        this.userReader = userReader;
        this.postFinder = postFinder;
        this.postReader = postReader;
        this.postUpdater = postUpdater;
        this.postDeleter = postDeleter;
    }

    @Transactional
    public void createPost(CreatePostCommand command) {
        User author = userReader.read(command.authorId());

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

    public Post getPostById(Long id) {
        return postReader.read(id);
    }

    @Transactional
    public void updatePost(Long id, UpdatePostCommand command) {
        postUpdater.update(id, command.newTitle(), command.newContent(), command.isAnonymous());
    }

    @Transactional
    public void deletePost(Long id) {
        postDeleter.delete(id);
    }
}