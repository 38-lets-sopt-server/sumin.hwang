package org.sopt.domain.post.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sopt.common.dto.PageOffset;
import org.sopt.common.dto.PageResult;
import org.sopt.domain.like.service.LikeService;
import org.sopt.domain.post.controller.dto.response.PostListResponse;
import org.sopt.domain.post.controller.dto.response.PostResponse;
import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.enums.BoardType;
import org.sopt.domain.post.service.PostService;
import org.sopt.domain.post.service.vo.CreatePostCommand;
import org.sopt.domain.post.service.vo.UpdatePostCommand;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.service.UserService;
import org.sopt.security.provider.PrincipalProvider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostFacade {

    private final PostService postService;
    private final LikeService likeService;
    private final UserService userService;

    @Transactional
    public void createPost(PrincipalProvider provider, CreatePostCommand command) {
        postService.createPost(provider.userId(), command);
    }

    public PostListResponse getAllPosts(PageOffset pageOffset, BoardType boardType) {
        PageResult<Post> posts = postService.getAllPosts(pageOffset, boardType);
        Map<Long, Long> likeMap = likeService.countLikes(posts.contents());
        Map<Long, User> authorMap = readAuthorMap(posts.contents());

        return PostListResponse.of(posts, authorMap, likeMap);
    }

    public PostListResponse searchPosts(String keyword, PageOffset pageOffset) {
        PageResult<Post> posts = postService.searchPosts(keyword, pageOffset);
        Map<Long, Long> likeMap = likeService.countLikes(posts.contents());
        Map<Long, User> authorMap = readAuthorMap(posts.contents());

        return PostListResponse.of(posts, authorMap, likeMap);
    }

    public PostResponse getPost(Long postId) {
        Post post = postService.getPostById(postId);
        long likeCount = likeService.countLike(postId);
        User author = userService.getUserById(post.getAuthorId());

        return PostResponse.from(post, author, likeCount);
    }

    @Transactional
    public void updatePost(PrincipalProvider provider, Long postId, UpdatePostCommand command) {
        postService.updatePost(provider.userId(), postId, command);
    }

    @Transactional
    public void deletePost(PrincipalProvider provider, Long postId) {
        postService.deletePost(provider.userId(), postId);
    }

    @Transactional
    public boolean toggleLike(PrincipalProvider provider, Long postId) {
        return likeService.toggleLike(postId, provider.userId());
    }

    private Map<Long, User> readAuthorMap(List<Post> posts) {
        Set<Long> authorIds = posts
                .stream()
                .map(Post::getAuthorId)
                .collect(Collectors.toUnmodifiableSet());

        return userService.getUserMapByIds(authorIds);
    }
}
