package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.response.UserStatusDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicUserStatusService implements UserStatusService {

  private final UserStatusRepository userStatusRepository;
  private final UserRepository userRepository;
  private final UserStatusMapper userStatusMapper;

  @Override
  @Transactional
  public UserStatusDto create(UserStatusCreateRequest request) {
    User user = findUserOrThrow(request.userId());
    validateNotDuplicated(request.userId());

    Instant lastActiveAt = request.lastActiveAt() != null ? request.lastActiveAt() : Instant.now();
    UserStatus userStatus = userStatusRepository.save(new UserStatus(user, lastActiveAt));

    return userStatusMapper.toDto(userStatus);
  }

  @Override
  public UserStatusDto find(UUID id) {
    UserStatus userStatus = findUserStatusOrThrow(id);
    return userStatusMapper.toDto(userStatus);
  }

  @Override
  public List<UserStatusDto> findAll() {
    return userStatusRepository.findAll().stream().map(userStatusMapper::toDto).toList();
  }

  @Override
  @Transactional
  public UserStatusDto update(UUID id, UserStatusUpdateRequest request) {
    UserStatus userStatus = findUserStatusOrThrow(id);
    userStatus.update(request.newLastActiveAt());

    return userStatusMapper.toDto(userStatus);
  }

  @Override
  @Transactional
  public UserStatusDto updateByUserId(UUID userId, Instant lastActiveAt) {
    UserStatus userStatus =
        userStatusRepository
            .findByUserId(userId)
            .orElseThrow(() -> new NoSuchElementException("상태 정보가 없는 유저 id: " + userId));

    userStatus.update(lastActiveAt);

    return userStatusMapper.toDto(userStatus);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    findUserStatusOrThrow(id);
    userStatusRepository.deleteById(id);
  }

  private User findUserOrThrow(UUID userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 id: " + userId));
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
