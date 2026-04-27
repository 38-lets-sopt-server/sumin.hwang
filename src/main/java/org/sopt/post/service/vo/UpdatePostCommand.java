package org.sopt.post.service.vo;

public record UpdatePostCommand(
        String newTitle,
        String newContent,
        boolean isAnonymous
) {

    public static UpdatePostCommand of(String newTitle, String newContent, boolean isAnonymous) {

        return new UpdatePostCommand(newTitle, newContent, isAnonymous);
    }
}
