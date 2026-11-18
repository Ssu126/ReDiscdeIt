package com.sprint.mission.discodeit.dto.response;

import com.sprint.mission.discodeit.entity.Message;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record MessageResponse(
    UUID id,
    String content,
    UUID authorId,
    UUID channelId,
    List<UUID> attachmentIds,
    Instant createdAt,
    Instant updatedAt) {

  public static MessageResponse toResponse(Message message) {
    return new MessageResponse(
        message.getId(),
        message.getContent(),
        message.getAuthorId(),
        message.getChannelId(),
        message.getAttachmentIds(),
        message.getCreatedAt(),
        message.getUpdatedAt());
  }
}
