package org.sopt.domain.auth.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenReader {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken readOrNull(Long userId) {
        return refreshTokenRepository.findByUserId(userId)
                .orElse(null);
    }
}
