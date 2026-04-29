package org.sopt.post.persistence;

import java.util.Optional;
import org.sopt.common.dto.PageOffset;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostRepository;
import org.sopt.post.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    public PostRepositoryImpl(PostJpaRepository postJpaRepository) {
        this.postJpaRepository = postJpaRepository;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id)
                .map(PostJpaEntity::toDomain);
    }

    @Override
    public Page<Post> findAll(PageOffset pageOffset) {
        return postJpaRepository.findAll(pageOffset.toPageRequest())
                .map(PostJpaEntity::toDomain);
    }

    @Override
    public Page<Post> findAllByBoardType(BoardType boardType, PageOffset pageOffset) {
        return postJpaRepository.findAllByBoardType(boardType, pageOffset.toPageRequest())
                .map(PostJpaEntity::toDomain);
    }

    @Override
    public Page<Post> searchByTitle(String keyword, PageOffset pageOffset) {
        return postJpaRepository.searchByTitle(keyword, pageOffset.toPageRequest())
                .map(PostJpaEntity::toDomain);
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostJpaEntity.fromDomain(post))
                .toDomain();
    }

    @Override
    public void delete(Post post) {
        postJpaRepository.deleteById(post.getId());
    }
}
