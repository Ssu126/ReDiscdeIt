package com.sprint.mission.discodeit.dto.request;

import java.util.List;
import java.util.UUID;

public record PrivateChannelCreateRequest(List<UUID> participantIds) {

  public PrivateChannelCreateRequest {
    if (participantIds == null || participantIds.isEmpty()) {
      throw new IllegalArgumentException("참여자가 없습니다.");
    }
    participantIds = participantIds.stream().distinct().toList();
  }
}
