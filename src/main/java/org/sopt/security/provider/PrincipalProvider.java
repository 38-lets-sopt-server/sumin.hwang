package org.sopt.security.provider;

public record PrincipalProvider(
        Long userId
) {

    public static PrincipalProvider of(final Long userId) {
        return new PrincipalProvider(userId);
    }
}
