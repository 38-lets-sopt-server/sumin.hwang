package org.sopt.domain.auth.domain;

import lombok.RequiredArgsConstructor;
import org.sopt.common.exception.BusinessException;
import org.sopt.domain.auth.code.AuthErrorCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenDeleter {

    private final RefreshTokenRepository refreshTokenRepository;

    public void delete(Long userId) {
        try {
            refreshTokenRepository.deleteByUserId(userId);
        } catch(Exception e) {
            e.printStackTrace();
            throw new BusinessException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}
