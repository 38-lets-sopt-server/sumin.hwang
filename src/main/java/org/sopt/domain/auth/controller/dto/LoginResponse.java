package org.sopt.domain.auth.controller.dto;

import org.sopt.domain.auth.service.vo.AuthTokens;

public record LoginResponse(
        String accessToken
) {

    public static LoginResponse from(AuthTokens tokens) {
        return new LoginResponse(tokens.accessToken());
    }
}
