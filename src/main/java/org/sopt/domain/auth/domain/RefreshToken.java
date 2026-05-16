package org.sopt.domain.auth.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "createWithId")
public class RefreshToken {

    private final Long id;
    private final Long userId;
    private String token;
    private LocalDateTime expiresAt;

    public void rotate(String newToken, long expiresInSeconds) {
        this.token = newToken;
        this.expiresAt = LocalDateTime.now().plusSeconds(expiresInSeconds);
    }

    public static RefreshToken create(Long userId, String token, long expiresInSeconds) {
        return new RefreshToken(null, userId, token, LocalDateTime.now().plusSeconds(expiresInSeconds));
    }
}
