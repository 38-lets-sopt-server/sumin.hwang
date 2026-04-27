package org.sopt.user.domain;

public record User(
        Long id,
        String nickname,
        String email
) {
}
