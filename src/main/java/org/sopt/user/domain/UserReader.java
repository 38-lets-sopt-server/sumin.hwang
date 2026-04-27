package org.sopt.user.domain;

import org.sopt.common.exception.BusinessException;
import org.sopt.user.code.UserErrorCode;
import org.sopt.user.persistence.UserJpaEntity;
import org.sopt.user.persistence.UserJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class UserReader {

    private final UserJpaRepository userJpaRepository;

    public UserReader(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public User read(Long id) {
        UserJpaEntity entity = userJpaRepository.findById(id)
                .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));

        return entity.toDomain();
    }
}
