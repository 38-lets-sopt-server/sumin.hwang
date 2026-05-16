package org.sopt.domain.auth.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenUpdater {

    private final RefreshTokenReader refreshTokenReader;
    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;
    private final RefreshTokenRepository refreshTokenRepository;

    public void update(Long userId, String newToken) {
        RefreshToken refreshToken = refreshTokenReader.readOrNull(userId);

        if (refreshToken != null) {
            refreshToken.rotate(newToken, refreshTokenExpiresInSeconds);
        } else {
            refreshToken = RefreshToken.create(userId, newToken, refreshTokenExpiresInSeconds);
        }

        refreshTokenRepository.save(refreshToken);
    }
}
