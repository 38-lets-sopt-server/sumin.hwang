package org.sopt.security;

import java.util.Collections;
import org.sopt.security.provider.PrincipalProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    private UserAuthentication(final PrincipalProvider provider) {
        super(provider, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public static UserAuthentication create(final PrincipalProvider provider) {
        return new UserAuthentication(provider);
    }
}