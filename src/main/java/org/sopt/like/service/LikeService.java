package org.sopt.like.service;

import java.util.List;
import java.util.Map;
import org.sopt.like.domain.LikeProcessor;
import org.sopt.like.domain.LikeReader;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostReader;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserReader;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeReader likeReader;
    private final PostReader postReader;
    private final UserReader userReader;
    private final LikeProcessor likeProcessor;

    public LikeService(LikeReader likeReader, PostReader postReader, UserReader userReader, LikeProcessor likeProcessor) {
        this.likeReader = likeReader;
        this.postReader = postReader;
        this.userReader = userReader;
        this.likeProcessor = likeProcessor;
    }

    public void like(Long postId, Long userId) {
        Post post = postReader.read(postId);
        User user = userReader.read(userId);

        likeProcessor.like(post, user);
    }

    public void unlike(Long postId, Long userId) {
        Post post = postReader.read(postId);
        User user = userReader.read(userId);

        likeProcessor.unlike(post, user);
    }

    public Map<Long, Long> countLikes(List<Post> posts) {
        return likeReader.countLikes(posts);
    }

    //추후 인증/인가 도입 시, 로그인한 사용자의 도입 여부를 포함하기 위해 미리 구현해두었습니다.
    public boolean isLiked(Long postId, Long userId) {
        Post post = postReader.read(postId);
        User user = userReader.read(userId);

        return likeReader.isLiked(post, user);
    }
}
