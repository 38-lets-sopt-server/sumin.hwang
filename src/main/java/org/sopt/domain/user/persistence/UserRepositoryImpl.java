package org.sopt.domain.user.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.sopt.domain.user.domain.User;
import org.sopt.domain.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id)
                .map(UserJpaEntity::toDomain);
    }

    @Override
    public Map<Long, User> findAllByIdsAsMap(Set<Long> ids) {
        return userJpaRepository.findAllById(ids)
                .stream()
                .collect(
                        Collectors.toUnmodifiableMap(
                                UserJpaEntity::getId,
                                UserJpaEntity::toDomain
                        )
                );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserJpaEntity::toDomain);
    }
}
