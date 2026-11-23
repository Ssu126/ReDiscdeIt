package com.sprint.mission.discodeit.dto.response;

import com.sprint.mission.discodeit.entity.BinaryContent;
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
        message.getAuthor() == null ? null : message.getAuthor().getId(),
        message.getChannel().getId(),
        message.getAttachments().stream().map(BinaryContent::getId).toList(),
        message.getCreatedAt(),
        message.getUpdatedAt());
  }
}
