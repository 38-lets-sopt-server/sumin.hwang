package org.sopt.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.common.dto.CommonResponse;
import org.sopt.domain.auth.controller.dto.LoginResponse;
import org.sopt.domain.auth.controller.dto.TokenReissueResponse;
import org.sopt.domain.auth.controller.dto.request.LoginRequest;
import org.sopt.security.provider.PrincipalProvider;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "인증 API")
public interface AuthApi {

    @Operation(
            summary = "로그인",
            description = "이메일/비밀번호로 로그인합니다. Access Token은 응답 바디로, Refresh Token은 HttpOnly 쿠키(Set-Cookie)로 발급됩니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "403", description = "비밀번호 불일치"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자"),
    })
    CommonResponse<LoginResponse> login(
            @RequestBody LoginRequest request,
            @Parameter(hidden = true) HttpServletResponse response
    );

    @Operation(
            summary = "토큰 재발급",
            description = "쿠키의 Refresh Token으로 Access Token과 Refresh Token을 재발급합니다. 새 Refresh Token은 HttpOnly 쿠키로 교체됩니다(Rotation)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "재발급 성공"),
            @ApiResponse(responseCode = "400", description = "refreshToken 쿠키 없음"),
            @ApiResponse(responseCode = "403", description = "유효하지 않은 Refresh Token"),
    })
    CommonResponse<TokenReissueResponse> reissue(
            @Parameter(in = ParameterIn.COOKIE, name = "refreshToken", required = true, description = "로그인 시 발급된 Refresh Token") String refreshToken,
            @Parameter(hidden = true) HttpServletResponse response
    );

    @Operation(
            summary = "로그아웃",
            description = "Refresh Token을 삭제하고 현재 Access Token을 블랙리스트에 등록합니다. 이후 해당 Access Token으로의 요청은 거부됩니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 요청"),
    })
    CommonResponse<Void> logout(
            @Parameter(hidden = true) PrincipalProvider provider,
            @Parameter(hidden = true) HttpServletRequest httpRequest
    );
}
