package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import lombok.Getter;

@Getter
public class User extends BaseUpdatableEntity {

  private String username;
  private String email;
  private String password;
  private BinaryContent profile;
  private UserStatus status;

  public User(String username, String email, String password, BinaryContent profile) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.profile = profile;
  }

  public void update(String username, String email, String password, BinaryContent profile) {
    if (username != null && !username.isBlank()) {
      this.username = username;
    }
    if (email != null && !email.isBlank()) {
      this.email = email;
    }
    if (password != null && !password.isBlank()) {
      this.password = password;
    }
    if (profile != null) {
      this.profile = profile;
    }
  }

  void setStatus(UserStatus status) {
    this.status = status;
  }
}
