package org.sopt.security;

import org.springframework.http.ResponseCookie;

public class RefreshTokenCookie {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    public static ResponseCookie create(String refreshToken, long expiresInSeconds) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .maxAge(expiresInSeconds)
                .build();
    }
}
