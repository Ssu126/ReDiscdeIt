package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import java.util.List;
import lombok.Getter;

@Getter
public class Message extends BaseUpdatableEntity {

  private String content;
  private User author;
  private Channel channel;
  private final List<BinaryContent> attachments;

  public Message(String content, User author, Channel channel, List<BinaryContent> attachments) {
    this.content = content;
    this.author = author;
    this.channel = channel;
    this.attachments = attachments == null ? List.of() : List.copyOf(attachments);
  }

  public void update(String content) {
    if (content != null && !content.isBlank()) {
      this.content = content;
    }
  }
}
