package org.sopt.domain.auth.domain;

import lombok.RequiredArgsConstructor;
import org.sopt.common.exception.BusinessException;
import org.sopt.domain.auth.code.AuthErrorCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenReader {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken readOrNull(final Long userId) {
        return refreshTokenRepository.findByUserId(userId)
                .orElse(null);
    }

    public RefreshToken read(final String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException(AuthErrorCode.INVALID_REFRESH_TOKEN));
    }
}
