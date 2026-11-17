package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {

  default boolean existsByUsername(String username) {
    return findAll().stream().anyMatch(user -> user.getUsername().equals(username));
  }

  default boolean existsByEmail(String email) {
    return findAll().stream().anyMatch(user -> user.getEmail().equals(email));
  }

  default Optional<User> findByUsername(String username) {
    return findAll().stream().filter(user -> user.getUsername().equals(username)).findFirst();
  }
}
