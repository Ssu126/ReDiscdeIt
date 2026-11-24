package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.response.ChannelDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelMapper channelMapper;

  @Override
  @Transactional
  public ChannelDto createPublic(PublicChannelCreateRequest request) {
    Channel channel =
        channelRepository.save(
            new Channel(request.name(), request.description(), ChannelType.PUBLIC));

    return channelMapper.toDto(channel);
  }

  @Override
  @Transactional
  public ChannelDto createPrivate(PrivateChannelCreateRequest request) {
    Channel channel = channelRepository.save(new Channel(null, null, ChannelType.PRIVATE));

    request
        .participantIds()
        .forEach(
            userId ->
                readStatusRepository.save(
                    new ReadStatus(findUserOrThrow(userId), channel, channel.getCreatedAt())));

    return channelMapper.toDto(channel);
  }

  @Override
  public ChannelDto find(UUID id) {
    Channel channel = findChannelOrThrow(id);

    return channelMapper.toDto(channel);
  }

  @Override
  public List<ChannelDto> findAllByUserId(UUID userId) {
    List<UUID> joinedChannelIds =
        readStatusRepository.findAllByUserId(userId).stream()
            .map(readStatus -> readStatus.getChannel().getId())
            .toList();
    return channelRepository.findAll().stream()
        .filter(
            channel ->
                channel.getType() == ChannelType.PUBLIC
                    || joinedChannelIds.contains(channel.getId()))
        .map(channelMapper::toDto)
        .toList();
  }

  @Override
  @Transactional
  public ChannelDto update(UUID id, ChannelUpdateRequest request) {
    Channel channel = findChannelOrThrow(id);

    if (channel.getType() == ChannelType.PRIVATE) {
      throw new IllegalArgumentException("PRIVATE 채널은 수정할 수 없습니다: " + channel.getId());
    }

    channel.update(request.newName(), request.newDescription());

    return channelMapper.toDto(channel);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    findChannelOrThrow(id);

    messageRepository.deleteByChannelId(id);
    channelRepository.deleteById(id);
  }

  private User findUserOrThrow(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 id: " + id));
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
        .map(readStatus -> readStatus.getUser().getId())
        .toList();
  }
}
