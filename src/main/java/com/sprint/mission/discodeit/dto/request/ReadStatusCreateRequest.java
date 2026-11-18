package com.sprint.mission.discodeit.dto.request;

import java.time.Instant;
import java.util.UUID;

public record ReadStatusCreateRequest(UUID userId, UUID channelId, Instant lastReadAt) {

  public ReadStatusCreateRequest {
    if (userId == null || channelId == null || lastReadAt == null) {
      throw new IllegalArgumentException("유저와 채널은 필수입니다.");
    }
  }
}
