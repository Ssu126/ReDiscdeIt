package com.sprint.mission.discodeit.dto.request;

import java.util.UUID;

public record MessageCreateRequest(String content, UUID authorId, UUID channelId) {

  public MessageCreateRequest {
    if (authorId == null || channelId == null) {
      throw new IllegalArgumentException("작성자와 채널은 필수입니다.");
    }
  }
}
