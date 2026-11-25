package com.sprint.mission.discodeit.dto.request;

import com.sprint.mission.discodeit.entity.BinaryContent;

public record BinaryContentCreateRequest(String filename, String contentType, byte[] bytes) {

  public BinaryContentCreateRequest {
    if (bytes == null || bytes.length == 0) {
      throw new IllegalArgumentException("파일 데이터가 비어 있습니다.");
    }
  }

  public BinaryContent toEntity() {
    return new BinaryContent(filename, contentType, (long) bytes.length);
  }
}
