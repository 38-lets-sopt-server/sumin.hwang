package org.sopt.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.common.dto.CommonResponse;
import org.sopt.post.controller.dto.request.CreatePostRequest;
import org.sopt.post.controller.dto.request.UpdatePostRequest;
import org.sopt.post.controller.dto.response.PostListResponse;
import org.sopt.post.controller.dto.response.PostResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Post", description = "게시글 API")
public interface PostApi {

    @Operation(summary = "게시글 작성", description = "새로운 게시글을 생성합니다.")
    CommonResponse<Void> createPost(@RequestBody CreatePostRequest request);

    @Operation(summary = "게시글 목록 조회", description = "게시판 종류, 페이지 등에 따른 게시글 목록을 조회합니다.")
    CommonResponse<PostListResponse> getAllPosts(
            @RequestParam(required = false, defaultValue = "FREE") String boardType,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    );

    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 게시글 상세 정보를 조회합니다.")
    CommonResponse<PostResponse> getPost(@PathVariable Long postId);

    @Operation(summary = "게시글 수정", description = "게시글 ID로 게시글 제목, 내용, 익명 여부를 수정합니다.")
    CommonResponse<Void> updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequest request);

    @Operation(summary = "게시글 삭제", description = "게시글 ID로 게시글을 조회합니다.")
    CommonResponse<Void> deletePost(@PathVariable Long postId);
}
