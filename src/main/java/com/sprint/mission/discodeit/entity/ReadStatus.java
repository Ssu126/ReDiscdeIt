package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ReadStatus extends Common {

  private static final long serialVersionUID = 1L;

  private final UUID userId;
  private final UUID channelId;
  private Instant lastReadAt;

  public ReadStatus(UUID userId, UUID channelId, Instant lastReadAt) {
    this.userId = userId;
    this.channelId = channelId;
    this.lastReadAt = lastReadAt;
  }

  public void update(Instant lastReadAt) {
    if (lastReadAt != null) {
      this.lastReadAt = lastReadAt;
      updateTimestamp();
    }
  }
}
