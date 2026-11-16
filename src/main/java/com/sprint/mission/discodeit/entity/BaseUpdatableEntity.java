package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import lombok.Getter;

@Getter
public abstract class BaseUpdatableEntity extends Common {

  private static final long serialVersionUID = 1L;
  private Instant updatedAt;

  protected void updateTimestamp() {
    updatedAt = Instant.now();
  }
}
