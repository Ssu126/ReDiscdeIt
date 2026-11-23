package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Message extends BaseUpdatableEntity {

  private String content;
  private UUID authorId;
  private UUID channelId;
  private final List<UUID> attachmentIds;

  public Message(String content, UUID authorId, UUID channelId, List<UUID> attachmentIds) {
    this.content = content;
    this.authorId = authorId;
    this.channelId = channelId;
    this.attachmentIds = attachmentIds == null ? List.of() : List.copyOf(attachmentIds);
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
