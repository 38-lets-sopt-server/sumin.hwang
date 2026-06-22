package org.sopt.domain.user.domain;

import lombok.RequiredArgsConstructor;
import org.sopt.security.oauth2.userinfo.OAuth2UserInfo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAppender {

    private final UserRepository userRepository;

    public User append(OAuth2UserInfo userInfo) {
        User newUser = User.createWithOAuth2(
                userInfo.getUsername(),
                userInfo.getEmail(),
                userInfo.getProvider(),
                userInfo.getSocialId()
        );

        return userRepository.save(newUser);
    }
}
