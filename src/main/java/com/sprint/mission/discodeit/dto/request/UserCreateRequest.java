package com.sprint.mission.discodeit.dto.request;

import com.sprint.mission.discodeit.entity.User;
import java.util.UUID;

public record UserCreateRequest(String username, String email, String password) {

  public User toEntity(UUID profileId) {
    return new User(username, email, password, profileId);
  }
}
