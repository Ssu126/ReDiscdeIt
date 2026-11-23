package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepository extends CrudRepository<UserStatus> {

  default Optional<UserStatus> findByUserId(UUID userId) {
    return findAll().stream()
        .filter(userStatus -> userStatus.getUser().getId().equals(userId))
        .findFirst();
  }

  default boolean existsByUserId(UUID userId) {
    return findAll().stream().anyMatch(userStatus -> userStatus.getUser().getId().equals(userId));
  }
}
