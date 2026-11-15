package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public abstract class Common implements Serializable {

  private static final long serialVersionUID = 1L;

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
