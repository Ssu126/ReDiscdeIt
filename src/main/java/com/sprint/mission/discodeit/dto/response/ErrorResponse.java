package com.sprint.mission.discodeit.dto.response;

import java.time.Instant;

public record ErrorResponse(Instant timeStamp, int status, String message) {

  public static ErrorResponse of(int status, String message) {
    return new ErrorResponse(Instant.now(), status, message);
  }
}
