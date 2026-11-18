package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.response.ChannelResponse;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;

  @Override
  public ChannelResponse createPublic(PublicChannelCreateRequest request) {
    Channel channel =
        channelRepository.save(
            new Channel(request.name(), request.description(), ChannelType.PUBLIC));

    return ChannelResponse.toResponse(channel, null, List.of());
  }

  @Override
  public ChannelResponse createPrivate(PrivateChannelCreateRequest request) {
    Instant now = Instant.now();

    Channel channel = channelRepository.save(new Channel(null, null, ChannelType.PRIVATE));

    request
        .participantIds()
        .forEach(userId -> readStatusRepository.save(new ReadStatus(userId, channel.getId(), now)));

    return ChannelResponse.toResponse(channel, null, request.participantIds());
  }

  @Override
  public ChannelResponse find(UUID id) {
    Channel channel = findChannelOrThrow(id);

    return ChannelResponse.toResponse(
        channel, findLastMessageAt(channel), findParticipantIds(channel));
  }

  @Override
  public List<ChannelResponse> findAllByUserId(UUID userId) {
    List<UUID> joinedChannelIds =
        readStatusRepository.findAllByUserId(userId).stream()
            .map(ReadStatus::getChannelId)
            .toList();
    return channelRepository.findAll().stream()
        .filter(
            channel ->
                channel.getType() == ChannelType.PUBLIC
                    || joinedChannelIds.contains(channel.getId()))
        .map(
            channel ->
                ChannelResponse.toResponse(
                    channel, findLastMessageAt(channel), findParticipantIds(channel)))
        .toList();
  }

  @Override
  public ChannelResponse update(ChannelUpdateRequest request) {
    Channel channel = findChannelOrThrow(request.id());

    if (channel.getType() == ChannelType.PRIVATE) {
      throw new IllegalArgumentException("PRIVATE 채널은 수정할 수 없습니다: " + channel.getId());
    }

    channel.update(request.name(), request.description());
    Channel updated = channelRepository.save(channel);
    return ChannelResponse.toResponse(
        updated, findLastMessageAt(updated), findParticipantIds(updated));
  }

  @Override
  public void delete(UUID id) {
    findChannelOrThrow(id);

    messageRepository.deleteByChannelId(id);
    readStatusRepository.deleteByChannelId(id);
    channelRepository.deleteById(id);
  }

  private Channel findChannelOrThrow(UUID id) {
    return channelRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 채널 id: " + id));
  }

  private Instant findLastMessageAt(Channel channel) {
    return messageRepository.findAllByChannelId(channel.getId()).stream()
        .map(Message::getCreatedAt)
        .max(Comparator.naturalOrder())
        .orElse(null);
  }

  private List<UUID> findParticipantIds(Channel channel) {
    if (channel.getType() != ChannelType.PRIVATE) {
      return List.of();
    }
    return readStatusRepository.findAllByChannelId(channel.getId()).stream()
        .map(ReadStatus::getUserId)
        .toList();
  }
}
