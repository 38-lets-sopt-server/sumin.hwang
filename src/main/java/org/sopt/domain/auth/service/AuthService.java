package org.sopt.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.common.exception.BusinessException;
import org.sopt.domain.auth.code.AuthErrorCode;
import org.sopt.domain.auth.domain.RefreshTokenUpdater;
import org.sopt.domain.auth.service.vo.LoginTokens;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.domain.UserReader;
import org.sopt.security.jwt.JwtProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserReader userReader;
    private final JwtProcessor jwtProcessor;
    private final RefreshTokenUpdater refreshTokenUpdater;

    @Transactional
    public LoginTokens login(String email, String password) {
        User loginUser = loginWithCredentials(email, password);
        Long loginUserId = loginUser.getId();

        String accessToken = jwtProcessor.generateAccessToken(loginUserId, loginUser.getEmail());
        String newRefreshToken = jwtProcessor.generateRefreshToken(loginUserId);

        // 기존 Refresh Token 삭제 후 새로 저장
        refreshTokenUpdater.update(loginUserId, newRefreshToken);

        return LoginTokens.of(accessToken, newRefreshToken);
    }

    private User loginWithCredentials(String email, String password) {
        User user = userReader.read(email);

        if (!user.getPassword().equals(password)) {
            throw new BusinessException(AuthErrorCode.PASSWORD_MISMATCH);
        }

        return user;
    }
}