package org.sopt.security.blacklist;

public interface BlacklistHandler {

    void add(String token);

    boolean isBlacklisted(String token);
}
