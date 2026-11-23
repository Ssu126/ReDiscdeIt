package com.sprint.mission.discodeit.dto.response;

import com.sprint.mission.discodeit.entity.ReadStatus;
import java.time.Instant;
import java.util.UUID;

public record ReadStatusResponse(
    UUID id,
    UUID userId,
    UUID channelId,
    Instant lastReadAt,
    Instant createdAt,
    Instant updatedAt) {

  public static ReadStatusResponse toResponse(ReadStatus readStatus) {
    return new ReadStatusResponse(
        readStatus.getId(),
        readStatus.getUser().getId(),
        readStatus.getChannel().getId(),
        readStatus.getLastReadAt(),
        readStatus.getCreatedAt(),
        readStatus.getUpdatedAt());
  }
}
