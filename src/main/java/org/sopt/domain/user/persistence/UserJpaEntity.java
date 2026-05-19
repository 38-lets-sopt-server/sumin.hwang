package org.sopt.domain.user.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.sopt.common.entity.BaseTimeEntity;
import org.sopt.domain.user.domain.User;
import org.sopt.security.oauth2.OAuth2Provider;
import org.springframework.stereotype.Component;

@Getter
@Entity(name = "User")
@Table(name = "users")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
public class UserJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private OAuth2Provider oAuth2Provider;

    private String oAuth2Id;

    protected UserJpaEntity() {
    }

    private UserJpaEntity(
            Long id,
            String nickname,
            String email,
            String password,
            OAuth2Provider oAuth2Provider,
            String oAuth2Id
    ) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.oAuth2Provider = oAuth2Provider;
        this.oAuth2Id = oAuth2Id;
    }

    public User toDomain() {
        return User.createWithId(id, nickname, email, password, oAuth2Provider, oAuth2Id);
    }

    public static UserJpaEntity from(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getPassword(),
                user.getOAuth2Provider(),
                user.getOAuth2Id()
        );
    }
}
