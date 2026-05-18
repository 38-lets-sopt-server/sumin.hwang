package org.sopt.domain.auth.controller.dto;

import org.sopt.domain.auth.service.vo.AuthTokens;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {

    public static LoginResponse from(AuthTokens tokens) {
        return new LoginResponse(tokens.accessToken(), tokens.refreshToken());
    }
}
