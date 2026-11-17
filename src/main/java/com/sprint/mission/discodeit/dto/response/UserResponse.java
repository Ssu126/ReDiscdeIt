package com.sprint.mission.discodeit.dto.response;

import com.sprint.mission.discodeit.entity.User;
import java.time.Instant;
import java.util.UUID;

public record UserResponse(
    UUID id,
    String username,
    String email,
    UUID profileId,
    boolean online,
    Instant createAt,
    Instant updateAt) {

  public static UserResponse toResponse(User user, boolean isOnline) {
    return new UserResponse(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getProfileId(),
        isOnline,
        user.getCreatedAt(),
        user.getUpdatedAt());
  }
}
