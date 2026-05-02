package org.sopt.post.domain;

import org.sopt.common.dto.PageOffset;
import org.sopt.post.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PostFinder {

    private final PostRepository postRepository;

    public PostFinder(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> find(BoardType boardType, PageOffset pageOffset) {
        if (boardType != null) {
            return postRepository.findAllByBoardType(boardType, pageOffset);
        }
        return postRepository.findAll(pageOffset);
    }

    public Page<Post> search(String keyword, PageOffset pageOffset) {
        return postRepository.searchByTitle(keyword, pageOffset);
    }
}