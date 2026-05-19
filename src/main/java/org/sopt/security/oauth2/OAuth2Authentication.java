package org.sopt.security.oauth2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.security.oauth2.userinfo.OAuth2UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@RequiredArgsConstructor(staticName = "create")
public class OAuth2Authentication implements OAuth2User {

    private final OAuth2UserInfo userInfo;

    @Override
    public String getName() {
        return userInfo.getUsername();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
