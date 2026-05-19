package org.sopt.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.common.exception.BusinessException;
import org.sopt.domain.auth.code.AuthErrorCode;
import org.sopt.domain.auth.domain.RefreshToken;
import org.sopt.domain.auth.domain.RefreshTokenDeleter;
import org.sopt.domain.auth.domain.RefreshTokenReader;
import org.sopt.domain.auth.domain.RefreshTokenRotator;
import org.sopt.domain.auth.service.vo.AuthTokens;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.domain.UserReader;
import org.sopt.security.blacklist.BlacklistHandler;
import org.sopt.security.jwt.JwtProcessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserReader userReader;
    private final JwtProcessor jwtProcessor;
    private final RefreshTokenRotator refreshTokenRotator;
    private final RefreshTokenReader refreshTokenReader;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenDeleter refreshTokenDeleter;
    private final BlacklistHandler blacklistHandler;

    @Transactional
    public AuthTokens login(final String email, final String password) {
        User loginUser = loginWithCredentials(email, password);
        Long loginUserId = loginUser.getId();

        return issueTokens(loginUserId, email);
    }

    @Transactional
    public AuthTokens reissueTokens(final String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenReader.read(refreshTokenValue);
        User user = userReader.read(refreshToken.getUserId());
        Long userId = user.getId();

        return issueTokens(userId, user.getEmail());
    }

    @Transactional
    public void logout(Long userId, String accessToken) {
        refreshTokenDeleter.delete(userId);
        blacklistHandler.add(accessToken);
    }

    private AuthTokens issueTokens(final Long userId, final String email) {
        String accessToken = jwtProcessor.generateAccessToken(userId, email);
        String refreshToken = jwtProcessor.generateRefreshToken(userId);

        refreshTokenRotator.rotate(userId, refreshToken);

        return AuthTokens.of(accessToken, refreshToken);
    }

    private User loginWithCredentials(final String email, final String password) {
        User user = userReader.read(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(AuthErrorCode.PASSWORD_MISMATCH);
        }

        return user;
    }
}