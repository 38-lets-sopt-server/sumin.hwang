package org.sopt.security.oauth2;

import java.util.Map;
import org.sopt.security.oauth2.userinfo.GoogleOAuth2UserInfo;
import org.sopt.security.oauth2.userinfo.OAuth2UserInfo;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo create(OAuth2Provider provider, Map<String, Object> attributes) {
        return switch (provider) {
            case GOOGLE -> GoogleOAuth2UserInfo.from(attributes);
            default -> null;
        };
    }
}
