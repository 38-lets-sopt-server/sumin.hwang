package org.sopt.common.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();

    HttpStatus getStatus();

    String getMessage();
}
