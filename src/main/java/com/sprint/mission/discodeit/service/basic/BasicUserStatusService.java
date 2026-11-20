package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.response.UserStatusResponse;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {

  private final UserStatusRepository userStatusRepository;
  private final UserRepository userRepository;

  @Override
  public UserStatusResponse create(UserStatusCreateRequest request) {
    validateUserExists(request.userId());
    validateNotDuplicated(request.userId());

    Instant lastActiveAt = request.lastActiveAt() != null ? request.lastActiveAt() : Instant.now();
    UserStatus userStatus =
        userStatusRepository.save(new UserStatus(request.userId(), lastActiveAt));

    return UserStatusResponse.toResponse(userStatus);
  }

  @Override
  public UserStatusResponse find(UUID id) {
    UserStatus userStatus = findUserStatusOrThrow(id);
    return UserStatusResponse.toResponse(userStatus);
  }

  @Override
  public List<UserStatusResponse> findAll() {
    return userStatusRepository.findAll().stream().map(UserStatusResponse::toResponse).toList();
  }

  @Override
  public UserStatusResponse update(UUID id, UserStatusUpdateRequest request) {
    UserStatus userStatus = findUserStatusOrThrow(id);

    userStatus.update(request.newLastActiveAt());
    UserStatus updated = userStatusRepository.save(userStatus);

    return UserStatusResponse.toResponse(updated);
  }

  @Override
  public UserStatusResponse updateByUserId(UUID userId, Instant lastActiveAt) {
    UserStatus userStatus =
        userStatusRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NoSuchElementException("상태 정보가 없는 유저 id: " + userId));

    userStatus.update(lastActiveAt);
    UserStatus updated = userStatusRepository.save(userStatus);

    return UserStatusResponse.toResponse(updated);
  }

  @Override
  public void delete(UUID id) {
    findUserStatusOrThrow(id);
    userStatusRepository.deleteById(id);
  }

  private void validateUserExists(UUID userId) {
    if (!userRepository.existsById(userId)) {
      throw new NoSuchElementException("존재하지 않은 유저 id: " + userId);
    }
  }

  private void validateNotDuplicated(UUID userId) {
    if (userStatusRepository.existsByUserId(userId)) {
      throw new IllegalArgumentException("이미 상태 정보가 존재하는 유저 id: " + userId);
    }
  }

  private UserStatus findUserStatusOrThrow(UUID id) {
    return userStatusRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 상태 id: " + id));
  }
}
