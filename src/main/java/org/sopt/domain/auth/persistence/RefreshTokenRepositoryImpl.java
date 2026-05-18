package org.sopt.domain.auth.persistence;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.domain.RefreshToken;
import org.sopt.domain.auth.domain.RefreshTokenRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenJpaRepository.save(RefreshTokenJpaEntity.from(refreshToken));
    }

    @Override
    public Optional<RefreshToken> findByUserId(Long userId) {
        return refreshTokenJpaRepository.findByUserId(userId)
                .map(RefreshTokenJpaEntity::toDomain);
    }
}
