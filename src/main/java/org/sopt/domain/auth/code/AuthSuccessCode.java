package org.sopt.domain.auth.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.common.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements SuccessCode {

    LOGIN_SUCCEED("로그인에 성공했어요!"),
    TOKEN_REISSUED("RefreshToken 재발급에 성공했습니다."),
    LOGOUT_SUCCEED("로그아웃에 성공했습니다.");

    private final String message;
}
