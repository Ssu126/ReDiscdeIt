package com.sprint.mission.discodeit.dto.response;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import java.util.UUID;

public record UserStatusResponse(
    UUID id,
    UUID userId,
    Instant lastActiveAt,
    Instant createdAt,
    Instant updatedAt,
    boolean online) {

  public static UserStatusResponse toResponse(UserStatus userStatus) {
    return new UserStatusResponse(
        userStatus.getId(),
        userStatus.getUser().getId(),
        userStatus.getLastActiveAt(),
        userStatus.getCreatedAt(),
        userStatus.getUpdatedAt(),
        userStatus.isOnline());
  }
}
