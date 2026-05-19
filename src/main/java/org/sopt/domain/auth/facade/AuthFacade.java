package org.sopt.domain.auth.facade;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.service.AuthService;
import org.sopt.domain.auth.service.vo.AuthTokens;
import org.sopt.security.jwt.AccessTokenExtractor;
import org.sopt.security.provider.PrincipalProvider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthFacade {

    private final AuthService authService;
    private final AccessTokenExtractor accessTokenExtractor;

    @Transactional
    public AuthTokens login(final String email, final String password) {
        return authService.login(email, password);
    }

    @Transactional
    public AuthTokens reissue(final String refreshToken) {
        return authService.reissueTokens(refreshToken);
    }

    @Transactional
    public void logout(PrincipalProvider provider, HttpServletRequest httpRequest) {
        String token = accessTokenExtractor.extract(httpRequest);
        authService.logout(provider.userId(), token);
    }
}
