package org.sopt.user.service;

import java.util.Map;
import java.util.Set;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserReader;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserReader userReader;

    public UserService(UserReader userReader) {
        this.userReader = userReader;
    }

    public User getUserById(Long id) {
        return userReader.read(id);
    }

    public Map<Long, User> getUserMapByIds(Set<Long> userIds) {
        return userReader.readMap(userIds);
    }
}
