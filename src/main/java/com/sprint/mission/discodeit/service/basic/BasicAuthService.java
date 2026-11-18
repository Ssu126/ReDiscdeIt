package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.dto.response.UserResponse;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {

  private final UserRepository userRepository;

  public UserResponse login(LoginRequest request) {
    User user =
        userRepository
            .findByUsername(request.username())
            .filter(u -> u.getPassword().equals(request.password()))
            .orElseThrow(() -> new IllegalArgumentException("이름 또는 비밀번호가 일치하지 않습니다."));

    return UserResponse.toResponse(user, true);
  }
}
