package org.sopt.security.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.sopt.security.UserAuthentication;
import org.sopt.security.blacklist.BlacklistHandler;
import org.sopt.security.provider.PrincipalProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProcessor jwtProcessor;
    private final AccessTokenExtractor accessTokenExtractor;
    private final BlacklistHandler blacklistHandler;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = accessTokenExtractor.extract(request);

        if (token == null || blacklistHandler.isBlacklisted(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Long userId = jwtProcessor.verifyAndGetUserId(token);
            PrincipalProvider provider = PrincipalProvider.of(userId);

            SecurityContextHolder.getContext().setAuthentication(UserAuthentication.create(provider));
        } catch (IllegalArgumentException | JWTVerificationException e) {
            // 유효하지 않은 토큰 또는 토큰이 없는 경우, 인증 없이 다음 필터로 넘겨요.
            // 여기서 예외를 던지지 않는 이유는, /v1/login 같이 인증이 필요 없는 API도
            // 이 필터를 거치기 때문이에요. 인증 여부 판단은 SecurityConfig의
            // authorizeHttpRequests 설정에서 담당합니다.
        }

        filterChain.doFilter(request, response);
    }
}