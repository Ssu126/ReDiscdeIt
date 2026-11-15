package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class JCFUserService implements UserService {

  private final Map<UUID, User> data;

  public JCFUserService() {
    data = new HashMap<>();
  }

  @Override
  public User create(String username, String email, String password) {
    User user = new User(username, email, password);
    data.put(user.getId(), user);
    return data.get(user.getId());
  }

  @Override
  public User find(UUID id) {
    return findByIdOrThrow(id);
  }

  @Override
  public List<User> findAll() {
    return new ArrayList<>(data.values());
  }

  @Override
  public User update(UUID id, String username, String email, String password) {
    User user = findByIdOrThrow(id);
    user.update(username, email, password);
    return user;
  }

  @Override
  public void delete(UUID id) {
    findByIdOrThrow(id);
    data.remove(id);
  }

  private User findByIdOrThrow(UUID id) {
    User user = data.get(id);
    if (user == null) {
      throw new NoSuchElementException("존재하지 않는 유저 id: " + id);
    }
    return user;
  }

}
