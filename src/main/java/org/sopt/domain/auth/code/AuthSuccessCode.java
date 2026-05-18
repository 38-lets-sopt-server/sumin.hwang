package org.sopt.domain.auth.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.common.code.SuccessCode;

@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements SuccessCode {

    LOGIN_SUCCEED("로그인에 성공했어요!");

    private final String message;
}
