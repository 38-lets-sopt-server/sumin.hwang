package org.sopt.domain.auth.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.controller.dto.LoginResponse;
import org.sopt.domain.auth.controller.dto.TokenReissueResponse;
import org.sopt.domain.auth.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthFacade {

    private final AuthService authService;

    @Transactional
    public LoginResponse login(final String email, final String password) {
        return LoginResponse.from(authService.login(email, password));
    }

    @Transactional
    public TokenReissueResponse reissue(final String refreshToken) {
        return TokenReissueResponse.from(authService.reissueTokens(refreshToken));
    }
}
