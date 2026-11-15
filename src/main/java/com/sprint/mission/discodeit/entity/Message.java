package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends Common {

  private String content;
  private UUID authorId;
  private UUID channelId;

  public Message(String content, UUID authorId, UUID channelId) {
    this.content = content;
    this.authorId = authorId;
    this.channelId = channelId;
  }

  public String getContent() {
    return content;
  }

  public UUID getAuthorId() {
    return authorId;
  }

  public UUID getChannelId() {
    return channelId;
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
