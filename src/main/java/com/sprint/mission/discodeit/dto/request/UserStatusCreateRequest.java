package com.sprint.mission.discodeit.dto.request;

import java.time.Instant;
import java.util.UUID;

public record UserStatusCreateRequest(UUID userId, Instant lastActiveAt) {

  public UserStatusCreateRequest {
    if (userId == null) {
      throw new IllegalArgumentException("유저는 필수입니다.");
    }
  }
}
