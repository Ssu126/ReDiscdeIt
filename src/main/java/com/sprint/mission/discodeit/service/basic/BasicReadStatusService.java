package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.response.ReadStatusResponse;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {

  private final ReadStatusRepository readStatusRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;

  @Override
  public ReadStatusResponse create(ReadStatusCreateRequest request) {
    validateUserExists(request.userId());
    validateChannelExists(request.channelId());
    validateNotDuplicated(request.userId(), request.channelId());

    ReadStatus readStatus =
        readStatusRepository.save(
            new ReadStatus(request.userId(), request.channelId(), request.lastReadAt()));

    return ReadStatusResponse.toResponse(readStatus);
  }

  @Override
  public ReadStatusResponse find(UUID id) {
    ReadStatus readStatus = findReadStatusOrThrow(id);
    return ReadStatusResponse.toResponse(readStatus);
  }

  @Override
  public List<ReadStatusResponse> findAllByUserId(UUID userId) {
    return readStatusRepository.findAllByUserId(userId).stream()
        .map(ReadStatusResponse::toResponse)
        .toList();
  }

  @Override
  public ReadStatusResponse update(ReadStatusUpdateRequest request) {
    ReadStatus readStatus = findReadStatusOrThrow(request.id());

    readStatus.update(request.lastReadAt());
    ReadStatus updated = readStatusRepository.save(readStatus);
    return ReadStatusResponse.toResponse(updated);
  }

  @Override
  public void delete(UUID id) {
    findReadStatusOrThrow(id);
    readStatusRepository.deleteById(id);
  }

  private void validateUserExists(UUID userId) {
    userRepository
        .findById(userId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 id: " + userId));
  }

  private void validateChannelExists(UUID channelId) {
    channelRepository
        .findById(channelId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 채널 id: " + channelId));
  }

  private void validateNotDuplicated(UUID userId, UUID channelId) {
    if (readStatusRepository.existsByUserIdAndChannelId(userId, channelId)) {
      throw new IllegalArgumentException(
          "이미 존재하는 읽음 상태: userId = " + userId + ", channelId = " + channelId);
    }
  }

  private ReadStatus findReadStatusOrThrow(UUID id) {
    return readStatusRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 읽음 상태 id: " + id));
  }
}
