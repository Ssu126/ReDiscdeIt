package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;

@Getter
public abstract class Common implements Serializable {

  private static final long serialVersionUID = 1L;

  private final UUID id;
  private final Long createdAt;
  private Long updatedAt;

  protected Common() {
    id = UUID.randomUUID();
    createdAt = System.currentTimeMillis();
  }

  protected void updateTimestamp() {
    updatedAt = System.currentTimeMillis();
  }
}
