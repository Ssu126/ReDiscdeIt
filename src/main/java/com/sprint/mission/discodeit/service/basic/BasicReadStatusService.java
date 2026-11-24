package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.response.ReadStatusResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicReadStatusService implements ReadStatusService {

  private final ReadStatusRepository readStatusRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;

  @Override
  @Transactional
  public ReadStatusResponse create(ReadStatusCreateRequest request) {
    User user = findUserOrThrow(request.userId());
    Channel channel = findChannelOrThrow(request.channelId());
    validateNotDuplicated(request.userId(), request.channelId());

    ReadStatus readStatus =
        readStatusRepository.save(new ReadStatus(user, channel, request.lastReadAt()));

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
  @Transactional
  public ReadStatusResponse update(UUID id, ReadStatusUpdateRequest request) {
    ReadStatus readStatus = findReadStatusOrThrow(id);
    readStatus.update(request.newLastReadAt());

    return ReadStatusResponse.toResponse(readStatus);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    findReadStatusOrThrow(id);
    readStatusRepository.deleteById(id);
  }

  private User findUserOrThrow(UUID userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 id: " + userId));
  }

  private Channel findChannelOrThrow(UUID channelId) {
    return channelRepository
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
