package org.sopt.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "createWithId")
public class User {

    private final Long id;
    private final String nickname;
    private final String email;
    private final String password;

    public static User create(String nickname, String email, String password) {
        return new User(null, nickname, email, password);
    }
}
