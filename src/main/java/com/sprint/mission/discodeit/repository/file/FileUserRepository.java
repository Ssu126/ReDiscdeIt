package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

public class FileUserRepository extends FileRepository<User> implements UserRepository {

  public FileUserRepository() {
    super("users", User.class);
  }

  @Override
  public boolean existsByUsername(String username) {
    return false;
  }

  @Override
  public boolean existsByEmail(String email) {
    return false;
  }
}
