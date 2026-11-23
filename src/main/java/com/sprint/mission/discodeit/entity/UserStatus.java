package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class UserStatus extends BaseUpdatableEntity {

  private final UUID userId;
  private Instant lastActiveAt;

  public UserStatus(UUID userId, Instant lastActiveAt) {
    this.userId = userId;
    this.lastActiveAt = lastActiveAt;
  }

  public void update(Instant lastActiveAt) {
    if (lastActiveAt != null) {
      this.lastActiveAt = lastActiveAt;
    }
  }

  public boolean isOnline() {
    return lastActiveAt.isAfter(Instant.now().minus(Duration.ofMinutes(5)));
  }
}
