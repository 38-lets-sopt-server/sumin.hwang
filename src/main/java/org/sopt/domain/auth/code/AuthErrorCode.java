package org.sopt.domain.auth.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    PASSWORD_MISMATCH(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다"),
    INVALID_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 Refresh Token입니다."),
    OAUTH2_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "OAuth2 로그인 인증에 실패했습니다.");

    private final HttpStatus status;
    private final String message;
}
