package org.sopt.domain.auth.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.controller.dto.LoginResponse;
import org.sopt.domain.auth.service.AuthService;
import org.sopt.domain.auth.service.vo.LoginTokens;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthFacade {

    private final AuthService authService;

    @Transactional
    public LoginResponse login(String email, String password) {
        LoginTokens tokens = authService.login(email, password);

        return LoginResponse.of(tokens.accessToken(), tokens.refreshToken());
    }
}
