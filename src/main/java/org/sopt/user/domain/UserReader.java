package org.sopt.user.domain;

import org.sopt.common.exception.BusinessException;
import org.sopt.user.code.UserErrorCode;
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
}
