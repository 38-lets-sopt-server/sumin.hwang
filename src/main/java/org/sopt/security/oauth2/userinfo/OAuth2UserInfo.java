package org.sopt.security.oauth2.userinfo;

import java.util.Map;
import org.sopt.security.oauth2.OAuth2Provider;

public interface OAuth2UserInfo {

    String getEmail();

    String getUsername();

    Map<String, Object> getAttributes();

    OAuth2Provider getProvider();

    String getSocialId();
}
