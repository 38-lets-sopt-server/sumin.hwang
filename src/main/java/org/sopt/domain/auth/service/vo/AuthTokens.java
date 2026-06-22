package org.sopt.domain.auth.service.vo;

public record AuthTokens(
        String accessToken,
        String refreshToken
) {

    public static AuthTokens of(String accessToken, String refreshToken) {
        return new AuthTokens(accessToken, refreshToken);
    }
}
