package com.sprint.mission.discodeit.entity;

import lombok.Getter;

@Getter
public class User extends Common {

  private static final long serialVersionUID = 1L;

  private String username;
  private String email;
  private String password;

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public void update(String username, String email, String password) {
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
    if (anyValueUpdated) {
      updateTimestamp();
    }
  }
}
