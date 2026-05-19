package org.sopt.security.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.auth.service.AuthService;
import org.sopt.domain.auth.service.vo.AuthTokens;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.service.UserService;
import org.sopt.security.oauth2.OAuth2Authentication;
import org.sopt.security.oauth2.userinfo.OAuth2UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final AuthService authService;

    @Value("${client.oauth2-redirect-uri}")
    private String clientRedirectUri;
    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;

    private static final String COOKIE_REFRESH_TOKEN = "refreshToken";

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2Authentication principal = (OAuth2Authentication) authentication.getPrincipal();
        OAuth2UserInfo userInfo = principal.getUserInfo();

        User user = userService.findOrCreateByOAuth2(userInfo);
        AuthTokens tokens = authService.loginWithOAuth2(user.getId(), user.getEmail());

        ResponseCookie refreshTokenCookie = ResponseCookie.from(COOKIE_REFRESH_TOKEN, tokens.refreshToken())
                .httpOnly(true)
                .path("/")
                //로컬 테스트용 설정 Secure=false; SameSite=Lax
                .secure(false)
                .sameSite("Lax")
                .maxAge(refreshTokenExpiresInSeconds)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        String redirectUri = UriComponentsBuilder
                .fromUriString(clientRedirectUri)
                .queryParam("token", tokens.accessToken())
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectUri);
    }
}
