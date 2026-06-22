package org.sopt.security.oauth2;

import java.util.Map;
import org.sopt.security.oauth2.userinfo.OAuth2UserInfo;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2AuthenticationService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        if (userRequest == null) {
            throw new OAuth2AuthenticationException("userRequest is null");
        }

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Provider provider = OAuth2Provider.from(registrationId);

        if (provider == OAuth2Provider.UNKNOWN) {
            throw new OAuth2AuthenticationException("Unknown provider: " + registrationId);
        }

        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.create(provider, attributes);

        return OAuth2Authentication.create(userInfo);
    }
}
