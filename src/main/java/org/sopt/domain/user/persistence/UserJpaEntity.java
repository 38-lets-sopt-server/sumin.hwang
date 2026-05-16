package org.sopt.domain.user.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.sopt.common.entity.BaseTimeEntity;
import org.sopt.domain.user.domain.User;

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

    protected UserJpaEntity() {}

    public UserJpaEntity(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    private UserJpaEntity(Long id, String nickname, String email, String password) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public User toDomain() {
        return User.createWithId(id, nickname, email, password);
    }

    public static UserJpaEntity create(String nickname, String email, String password) {
        return new UserJpaEntity(nickname, email, password);
    }

    public static UserJpaEntity from(User user) {
        return new UserJpaEntity(user.getId(), user.getNickname(), user.getEmail(), user.getPassword());
    }
}
