package org.sopt.dto.response;

import java.util.List;
import org.sopt.domain.Post;

public record GetAllPostsResponse(
        List<PostResponse> posts
) {
    public static GetAllPostsResponse of(List<Post> posts) {
        return new GetAllPostsResponse(
                posts.stream().map(PostResponse::from).toList()
        );
    }

    @Override
    public String toString() {
        List<String> printTexts = posts.stream().map(PostResponse::toString).toList();

        return String.join("\n---\n", printTexts);
    }
}
