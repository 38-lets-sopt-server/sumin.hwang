package org.sopt.security.oauth2;

public enum OAuth2Provider {
    GOOGLE,
    KAKAO,
    UNKNOWN;

    public static OAuth2Provider from(String provider) {
        try {
            return valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
