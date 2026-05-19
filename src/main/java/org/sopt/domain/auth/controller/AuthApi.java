package org.sopt.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.common.dto.CommonResponse;
import org.sopt.domain.auth.controller.dto.LoginResponse;
import org.sopt.domain.auth.controller.dto.request.LoginRequest;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "인증 API")
public interface AuthApi {

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    CommonResponse<LoginResponse> login(
            @RequestBody LoginRequest request
    );
}
