package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public abstract class Common implements Serializable {

  private static final long serialVersionUID = 1L;

  private final UUID id;
  private final Instant createdAt;
  private Instant updatedAt;

  protected Common() {
    id = UUID.randomUUID();
    createdAt = Instant.now();
  }

  protected void updateTimestamp() {
    updatedAt = Instant.now();
  }
}
