package com.sprint.mission.discodeit.dto.response;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChannelResponse(
    UUID id,
    String name,
    String description,
    ChannelType type,
    Instant lastMessagedAt,
    List<UUID> participantIds,
    Instant createdAt,
    Instant updatedAt) {

  public static ChannelResponse toResponse(
      Channel channel, Instant lsatMessagedAt, List<UUID> participantIds) {
    return new ChannelResponse(
        channel.getId(),
        channel.getName(),
        channel.getDescription(),
        channel.getType(),
        lsatMessagedAt,
        participantIds,
        channel.getCreatedAt(),
        channel.getUpdatedAt());
  }
}
