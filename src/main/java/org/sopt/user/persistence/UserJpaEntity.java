package org.sopt.user.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.sopt.user.domain.User;

@Entity
@Table(name = "users")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    protected UserJpaEntity() {}

    public UserJpaEntity(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    private UserJpaEntity(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public User toDomain() {
        return new User(id, nickname, email);
    }

    public static UserJpaEntity create(String nickname, String email) {
        return new UserJpaEntity(nickname, email);
    }

    public static UserJpaEntity from(User user) {
        return new UserJpaEntity(user.id(), user.nickname(), user.email());
    }
}
