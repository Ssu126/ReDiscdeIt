package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JCFUserRepository extends JCFRepository<User> implements UserRepository {

  @Override
  public boolean existsByUsername(String username) {
    return findAll().stream().anyMatch(user -> user.getUsername().equals(username));
  }

  @Override
  public boolean existsByEmail(String email) {
    return findAll().stream().anyMatch(user -> user.getEmail().equals(email));
  }
}
