package org.sopt.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.sopt.common.dto.CommonResponse;
import org.sopt.domain.auth.code.AuthSuccessCode;
import org.sopt.domain.auth.controller.dto.TokenResponse;
import org.sopt.domain.auth.service.AuthService;
import org.sopt.domain.auth.service.vo.LoginTokens;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    public CommonResponse<TokenResponse> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        LoginTokens tokens = authService.login(email, password);

        return CommonResponse.success(
                AuthSuccessCode.LOGIN_SUCCEED,
                TokenResponse.of(tokens.accessToken(), tokens.refreshToken())
        );
    }
}
