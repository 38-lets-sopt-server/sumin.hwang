package org.sopt.domain.auth.controller.dto.request;

public record LoginRequest(
        String email,
        String password
) {

}
