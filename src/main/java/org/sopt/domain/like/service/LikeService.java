package org.sopt.domain.like.service;

import java.util.List;
import java.util.Map;
import org.sopt.domain.like.domain.LikeProcessor;
import org.sopt.domain.like.domain.LikeReader;
import org.sopt.domain.post.domain.Post;
import org.sopt.domain.post.domain.PostReader;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.domain.UserReader;
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

    public boolean toggleLike(Long postId, Long userId) {
        Post post = postReader.read(postId);
        User user = userReader.read(userId);

        return likeProcessor.toggleLike(post, user);
    }

    public Map<Long, Long> countLikes(List<Post> posts) {
        return likeReader.countLikes(posts);
    }

    public Long countLike(Long postId) {
        Post post = postReader.read(postId);

        return likeReader.countLike(post);
    }

    //추후 인증/인가 도입 시, 로그인한 사용자의 도입 여부를 포함하기 위해 미리 구현해두었습니다.
    public boolean isLiked(Long postId, Long userId) {
        Post post = postReader.read(postId);
        User user = userReader.read(userId);

        return likeReader.isLiked(post, user);
    }
}
