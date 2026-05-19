package org.sopt.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.sopt.common.dto.CommonResponse;
import org.sopt.domain.auth.code.AuthSuccessCode;
import org.sopt.domain.auth.controller.dto.LoginResponse;
import org.sopt.domain.auth.controller.dto.TokenReissueResponse;
import org.sopt.domain.auth.controller.dto.request.RefreshTokenRequest;
import org.sopt.domain.auth.facade.AuthFacade;
import org.sopt.security.provider.PrincipalProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController implements AuthApi {

    private final AuthFacade authFacade;

    @PostMapping("/login")
    public CommonResponse<LoginResponse> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        return CommonResponse.success(AuthSuccessCode.LOGIN_SUCCEED, authFacade.login(email, password));
    }

    @PostMapping("/reissue")
    public CommonResponse<TokenReissueResponse> reissue(
            @RequestBody RefreshTokenRequest request
    ) {
        return CommonResponse.success(AuthSuccessCode.TOKEN_REISSUED, authFacade.reissue(request.refreshToken()));
    }

    @PostMapping("/logout")
    public CommonResponse<Void> logout(PrincipalProvider provider, HttpServletRequest httpRequest) {
        authFacade.logout(provider, httpRequest);
        return CommonResponse.success(AuthSuccessCode.LOGOUT_SUCCEED);
    }
}
