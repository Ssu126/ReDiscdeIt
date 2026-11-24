package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.response.ChannelDto;
import com.sprint.mission.discodeit.dto.response.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelMapper {

  private final MessageRepository messageRepository;
  private final ReadStatusRepository readStatusRepository;
  private final UserMapper userMapper;

  public ChannelDto toDto(Channel channel) {
    return new ChannelDto(
        channel.getId(),
        channel.getName(),
        channel.getDescription(),
        channel.getType(),
        findLastMessageAt(channel),
        findParticipants(channel));
  }

  private Instant findLastMessageAt(Channel channel) {
    return messageRepository.findAllByChannelId(channel.getId()).stream()
        .map(Message::getCreatedAt)
        .max(Comparator.naturalOrder())
        .orElse(null);
  }

  private List<UserDto> findParticipants(Channel channel) {
    if (channel.getType() != ChannelType.PRIVATE) {
      return List.of();
    }
    return readStatusRepository.findAllByChannelId(channel.getId()).stream()
        .map(readStatus -> userMapper.toDto(readStatus.getUser()))
        .toList();
  }
}
