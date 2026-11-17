package com.sprint.mission.discodeit.dto.request;

public record BinaryContentCreateRequest(
    String filename,
    String contentType,
    byte[] bytes

) {

  public BinaryContentCreateRequest {
    if (bytes == null || bytes.length == 0) {
      throw new IllegalArgumentException("파일 데이터가 비어 있습니다.");
    }
  }
}
