package org.sopt.domain.user.service;

import java.util.Map;
import java.util.Set;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.domain.UserAppender;
import org.sopt.domain.user.domain.UserReader;
import org.sopt.security.oauth2.OAuth2Provider;
import org.sopt.security.oauth2.userinfo.OAuth2UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserReader userReader;
    private final UserAppender userAppender;

    public UserService(UserReader userReader, UserAppender userAppender) {
        this.userReader = userReader;
        this.userAppender = userAppender;
    }

    public User getUserById(Long id) {
        return userReader.read(id);
    }

    public User findOrCreateByOAuth2(OAuth2UserInfo userInfo) {
        try {
            return userReader.read(userInfo.getEmail());
        } catch (Exception e) {
            return userAppender.append(userInfo);
        }
    }

    public Map<Long, User> getUserMapByIds(Set<Long> userIds) {
        return userReader.readMap(userIds);
    }
}
