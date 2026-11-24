package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.dto.response.UserResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicUserService implements UserService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserResponse create(
      UserCreateRequest request, Optional<BinaryContentCreateRequest> profile) {
    validateDuplicateUsername(request.username());
    validateDuplicateEmail(request.email());

    BinaryContent profileContent = profile.map(BinaryContentCreateRequest::toEntity).orElse(null);
    User user = new User(request.username(), request.email(), request.password(), profileContent);
    new UserStatus(user, Instant.now());

    User savedUser = userRepository.save(user);

    return UserResponse.toResponse(savedUser, isOnline(savedUser));
  }

  @Override
  public UserResponse find(UUID id) {
    User user = findUserOrThrow(id);
    return UserResponse.toResponse(user, isOnline(user));
  }

  @Override
  public List<UserResponse> findAll() {
    return userRepository.findAll().stream()
        .map(user -> UserResponse.toResponse(user, isOnline(user)))
        .toList();
  }

  @Override
  @Transactional
  public UserResponse update(
      UUID id, UserUpdateRequest updateRequest, Optional<BinaryContentCreateRequest> profile) {
    User user = findUserOrThrow(id);

    if (updateRequest.newUsername() != null
        && !updateRequest.newUsername().equals(user.getUsername())) {
      validateDuplicateUsername(updateRequest.newUsername());
    }
    if (updateRequest.newEmail() != null && !updateRequest.newEmail().equals(user.getEmail())) {
      validateDuplicateEmail(updateRequest.newEmail());
    }

    BinaryContent newProfile = profile.map(BinaryContentCreateRequest::toEntity).orElse(null);
    user.update(
        updateRequest.newUsername(),
        updateRequest.newEmail(),
        updateRequest.newPassword(),
        newProfile);

    return UserResponse.toResponse(user, isOnline(user));
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    User user = findUserOrThrow(id);
    userRepository.delete(user);
  }

  private boolean isOnline(User user) {
    UserStatus status = user.getStatus();
    return status != null && status.isOnline();
  }

  private void validateDuplicateUsername(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new IllegalArgumentException("이미 존재하는 이름: " + username);
    }
  }

  private void validateDuplicateEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("이미 존재하는 이메일: " + email);
    }
  }

  private User findUserOrThrow(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 id: " + id));
  }
}
