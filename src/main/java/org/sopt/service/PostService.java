package org.sopt.service;

import java.time.LocalDateTime;
import java.util.List;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.DeletePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.dto.response.UpdatePostResponse;
import org.sopt.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository = new PostRepository();

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다!");
        }
        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다!");
        }
        String createdAt = LocalDateTime.now().toString();
        Post post = new Post(postRepository.generateId(), request.getTitle(), request.getContent(), request.getAuthor(), createdAt);
        postRepository.save(post);
        return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ - 전체 📝 과제
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::new)
                .toList();
    }

    // READ - 단건 📝 과제
    public PostResponse getPost(Long id) {
        Post post = findOrThrow(id);

        return new PostResponse(post);
    }

    // UPDATE 📝 과제
    public UpdatePostResponse updatePost(Long id, String newTitle, String newContent) {
        Post post = findOrThrow(id);
        post.update(newTitle, newContent);

        return new UpdatePostResponse(post.getId(), "게시글 수정 완료!");
    }

    // DELETE 📝 과제
    public DeletePostResponse deletePost(Long id) {
        postRepository.deleteById(id);

        return new DeletePostResponse(id, "게시글 삭제 완료!");
    }

    private Post findOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }
}