package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

  private final UserRepository userRepository;

  @Override
  public User create(String username, String email, String password) {
    User user = new User(username, email, password);
    return userRepository.save(user);
  }

  @Override
  public User find(UUID id) {
    return findUserOrThrow(id);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User update(UUID id, String username, String email, String password) {
    User user = findUserOrThrow(id);
    user.update(username, email, password);
    return userRepository.save(user);
  }

  @Override
  public void delete(UUID id) {
    findUserOrThrow(id);
    userRepository.deleteById(id);
  }

  private User findUserOrThrow(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 id: " + id));
  }
}
