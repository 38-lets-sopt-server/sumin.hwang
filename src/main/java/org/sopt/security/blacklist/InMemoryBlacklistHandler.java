package org.sopt.security.blacklist;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryBlacklistHandler implements BlacklistHandler {

    private final ConcurrentHashMap<String, Long> blacklist = new ConcurrentHashMap<>();

    @Override
    public void add(String token) {
        blacklist.put(token, System.currentTimeMillis());
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
