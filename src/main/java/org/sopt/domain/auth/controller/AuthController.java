package org.sopt.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sopt.common.dto.CommonResponse;
import org.sopt.domain.auth.code.AuthSuccessCode;
import org.sopt.domain.auth.controller.dto.LoginResponse;
import org.sopt.domain.auth.controller.dto.TokenReissueResponse;
import org.sopt.domain.auth.controller.dto.request.LoginRequest;
import org.sopt.domain.auth.facade.AuthFacade;
import org.sopt.domain.auth.service.vo.AuthTokens;
import org.sopt.security.RefreshTokenCookie;
import org.sopt.security.provider.PrincipalProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController implements AuthApi {

    private final AuthFacade authFacade;

    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;

    @PostMapping("/login")
    public CommonResponse<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        AuthTokens tokens = authFacade.login(request.email(), request.password());
        setRefreshTokenCookie(response, tokens.refreshToken());
        return CommonResponse.success(AuthSuccessCode.LOGIN_SUCCEED, LoginResponse.from(tokens));
    }

    @PostMapping("/reissue")
    public CommonResponse<TokenReissueResponse> reissue(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        AuthTokens tokens = authFacade.reissue(refreshToken);
        setRefreshTokenCookie(response, tokens.refreshToken());
        return CommonResponse.success(AuthSuccessCode.TOKEN_REISSUED, TokenReissueResponse.from(tokens));
    }

    @PostMapping("/logout")
    public CommonResponse<Void> logout(PrincipalProvider provider, HttpServletRequest httpRequest) {
        authFacade.logout(provider, httpRequest);
        return CommonResponse.success(AuthSuccessCode.LOGOUT_SUCCEED);
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = RefreshTokenCookie.create(refreshToken, refreshTokenExpiresInSeconds);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
