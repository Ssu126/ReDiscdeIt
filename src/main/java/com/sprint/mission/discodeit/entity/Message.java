package com.sprint.mission.discodeit.entity;

import java.util.UUID;
import lombok.Getter;

@Getter
public class Message extends Common {

  private static final long serialVersionUID = 1L;

  private String content;
  private UUID authorId;
  private UUID channelId;

  public Message(String content, UUID authorId, UUID channelId) {
    this.content = content;
    this.authorId = authorId;
    this.channelId = channelId;
  }

  public void update(String content) {
    boolean anyValueUpdated = false;
    if (content != null && !content.isBlank()) {
      this.content = content;
      anyValueUpdated = true;
    }
    if (anyValueUpdated) {
      updateTimestamp();
    }
  }
}
