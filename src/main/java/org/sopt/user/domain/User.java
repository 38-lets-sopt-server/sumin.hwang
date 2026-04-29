package org.sopt.user.domain;

public class User {

    private final Long id;
    private final String nickname;
    private final String email;

    private User(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public static User createWithId(Long id, String nickname, String email) {
        return new User(id, nickname, email);
    }

    public static User create(String nickname, String email) {
        return new User(null, nickname, email);
    }
}
