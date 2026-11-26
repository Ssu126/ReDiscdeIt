package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.dto.response.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import java.time.Instant;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicAuthService implements AuthService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public UserDto login(LoginRequest request) {
    User user =
        userRepository
            .findByUsername(request.username())
            .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다: " + request.username()));

    if (!user.getPassword().equals(request.password())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    user.getStatus().update(Instant.now());

    return userMapper.toDto(user);
  }
}
