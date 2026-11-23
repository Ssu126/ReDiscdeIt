package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import java.util.UUID;
import lombok.Getter;

@Getter
public class User extends BaseUpdatableEntity {

  private String username;
  private String email;
  private String password;
  private UUID profileId;

  public User(String username, String email, String password, UUID profileId) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.profileId = profileId;
  }

  public void update(String username, String email, String password, UUID profileId) {
    if (username != null && !username.isBlank()) {
      this.username = username;
    }
    if (email != null && !email.isBlank()) {
      this.email = email;
    }
    if (password != null && !password.isBlank()) {
      this.password = password;
    }
    if (profileId != null) {
      this.profileId = profileId;
    }
  }
}
