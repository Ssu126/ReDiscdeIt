package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public abstract class Common {

  private final UUID id;
  private final Long createdAt;
  private Long updatedAt;

  protected Common() {
    id = UUID.randomUUID();
    createdAt = System.currentTimeMillis();
  }

  public UUID getId() {
    return id;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public Long getUpdatedAt() {
    return updatedAt;
  }

  protected void updateTimestamp() {
    updatedAt = System.currentTimeMillis();
  }
}
