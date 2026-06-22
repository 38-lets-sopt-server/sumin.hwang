package org.sopt.security.blacklist;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InMemoryBlacklistHandler implements BlacklistHandler {

    private final ConcurrentHashMap<String, Long> blacklist = new ConcurrentHashMap<>();

    @Value("${security.jwt.access-token-expires-in-seconds}")
    private long accessTokenExpiresInSeconds;

    @Override
    public void add(String token) {
        blacklist.put(token, System.currentTimeMillis() + accessTokenExpiresInSeconds * 1000);
    }

    @Override
    public boolean isBlacklisted(String token) {
        if (blacklist.containsKey(token)) {
            return !removeIfExpired(token);
        }

        return false;
    }

    private boolean removeIfExpired(String token) {
        Long expirationTime = blacklist.get(token);

        if (expirationTime != null && expirationTime < System.currentTimeMillis()) {
            blacklist.remove(token);
            return true;
        }

        return false;
    }
}
