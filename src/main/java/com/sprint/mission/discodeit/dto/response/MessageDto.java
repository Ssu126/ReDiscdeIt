package com.sprint.mission.discodeit.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record MessageDto(
    UUID id,
    String content,
    UserDto author,
    UUID channelId,
    List<BinaryContentDto> attachments,
    Instant createdAt,
    Instant updatedAt) {}
