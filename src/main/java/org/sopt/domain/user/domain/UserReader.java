package org.sopt.domain.user.domain;

import java.util.Map;
import java.util.Set;
import org.sopt.common.exception.BusinessException;
import org.sopt.domain.user.code.UserErrorCode;
import org.springframework.stereotype.Component;

@Component
public class UserReader {

    private final UserRepository userRepository;

    public UserReader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User read(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    }

    public User read(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
    }

    public Map<Long, User> readMap(Set<Long> ids) {
        return userRepository.findAllByIdsAsMap(ids);
    }
}
