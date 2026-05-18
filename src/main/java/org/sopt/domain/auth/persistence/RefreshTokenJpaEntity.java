package org.sopt.domain.auth.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.domain.auth.domain.RefreshToken;

@Entity(name = "RefreshToken")
@Table(name = "refresh_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private RefreshTokenJpaEntity(Long id, Long userId, String token, LocalDateTime expiresAt) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    private RefreshTokenJpaEntity(Long userId, String token, LocalDateTime expiresAt) {
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public static RefreshTokenJpaEntity create(Long userId, String token, LocalDateTime expiresAt) {
        return new RefreshTokenJpaEntity(userId, token, expiresAt);
    }
    public RefreshToken toDomain() {
        return RefreshToken.createWithId(id, userId, token, expiresAt);
    }

    public static RefreshTokenJpaEntity from(RefreshToken refreshToken) {
        return new RefreshTokenJpaEntity(
                refreshToken.getId(),
                refreshToken.getUserId(),
                refreshToken.getToken(),
                refreshToken.getExpiresAt()
        );
    }
}