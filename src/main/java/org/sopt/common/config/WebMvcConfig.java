package org.sopt.common.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.security.provider.PrincipalProviderArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final PrincipalProviderArgumentResolver principalProviderArgumentResolver;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(principalProviderArgumentResolver);
    }
}
