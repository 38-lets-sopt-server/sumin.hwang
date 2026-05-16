package org.sopt.domain.user.domain;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserRepository {

    Optional<User> findById(Long id);

    Map<Long, User> findAllByIdsAsMap(Set<Long> ids);

    Optional<User> findByEmail(String email);
}
