package com.sprint.mission.discodeit.entity;

import java.util.UUID;
import lombok.Getter;

@Getter
public class User extends BaseUpdatableEntity {

  private static final long serialVersionUID = 1L;

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
    boolean anyValueUpdated = false;
    if (username != null && !username.isBlank()) {
      this.username = username;
      anyValueUpdated = true;
    }
    if (email != null && !email.isBlank()) {
      this.email = email;
      anyValueUpdated = true;
    }
    if (password != null && !password.isBlank()) {
      this.password = password;
      anyValueUpdated = true;
    }
    if (profileId != null) {
      this.profileId = profileId;
      anyValueUpdated = true;
    }
    if (anyValueUpdated) {
      updateTimestamp();
    }
  }
}
