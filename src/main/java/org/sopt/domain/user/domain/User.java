package org.sopt.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.security.oauth2.OAuth2Provider;

@Getter
@AllArgsConstructor(staticName = "createWithId")
public class User {

    private final Long id;
    private final String nickname;
    private final String email;
    private final String password;
    private final OAuth2Provider oAuth2Provider;
    private final String oAuth2Id;

    public static User create(String nickname, String email, String password) {
        return new User(null, nickname, email, password, null, null);
    }

    public static User createWithOAuth2(String nickname, String email, OAuth2Provider oAuth2Provider, String oAuth2Id) {
        return new User(null, nickname, email, null, oAuth2Provider, oAuth2Id);
    }
}
