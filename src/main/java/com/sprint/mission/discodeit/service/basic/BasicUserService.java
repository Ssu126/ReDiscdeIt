package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.dto.response.UserResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

  private final UserRepository userRepository;
  private final UserStatusRepository statusRepository;
  private final BinaryContentRepository contentRepository;

  @Override
  public UserResponse create(
      UserCreateRequest request, Optional<BinaryContentCreateRequest> profile) {
    validateDuplicateUsername(request.username());
    validateDuplicateEmail(request.email());

    BinaryContent profileContent = profile.map(this::saveProfile).orElse(null);
    User user =
        userRepository.save(
            new User(request.username(), request.email(), request.password(), profileContent));
    UserStatus userStatus = statusRepository.save(new UserStatus(user, Instant.now()));

    return UserResponse.toResponse(user, userStatus.isOnline());
  }

  @Override
  public UserResponse find(UUID id) {
    User user = findUserOrThrow(id);
    boolean online = statusRepository.findByUserId(id).map(UserStatus::isOnline).orElse(false);

    return UserResponse.toResponse(user, online);
  }

  @Override
  public List<UserResponse> findAll() {
    return userRepository.findAll().stream()
        .map(
            user ->
                UserResponse.toResponse(
                    user,
                    statusRepository
                        .findByUserId(user.getId())
                        .map(UserStatus::isOnline)
                        .orElse(false)))
        .toList();
  }

  @Override
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

    BinaryContent newProfile = null;
    if (profile.isPresent()) {
      BinaryContent oldProfile = user.getProfile();
      newProfile = saveProfile(profile.get());
      if (oldProfile != null) {
        contentRepository.deleteById(oldProfile.getId());
      }
    }

    user.update(
        updateRequest.newUsername(),
        updateRequest.newEmail(),
        updateRequest.newPassword(),
        newProfile);
    User updatedUser = userRepository.save(user);

    boolean online = statusRepository.findByUserId(id).map(UserStatus::isOnline).orElse(false);
    return UserResponse.toResponse(updatedUser, online);
  }

  @Override
  public void delete(UUID id) {
    User user = findUserOrThrow(id);
    if (user.getProfile() != null) {
      contentRepository.deleteById(user.getProfile().getId());
    }
    statusRepository
        .findByUserId(id)
        .ifPresent(userStatus -> statusRepository.deleteById(userStatus.getId()));
    userRepository.deleteById(id);
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

  private BinaryContent saveProfile(BinaryContentCreateRequest profile) {
    BinaryContent binaryContent =
        new BinaryContent(profile.filename(), profile.contentType(), profile.bytes());
    return contentRepository.save(binaryContent);
  }

  private User findUserOrThrow(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 id: " + id));
  }
}
