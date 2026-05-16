package org.sopt.domain.auth.domain;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);

    Optional<RefreshToken> findByUserId(Long userId);
}
