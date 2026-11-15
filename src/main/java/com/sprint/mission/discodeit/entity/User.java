package com.sprint.mission.discodeit.entity;

public class User extends Common {

  private String username;
  private String email;
  private String password;

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void update(String username, String email, String passwrod) {
    boolean anyValueUpdated = false;
    if (username != null && !username.isBlank()) {
      this.username = username;
      anyValueUpdated = true;
    }
    if (email != null && !email.isBlank()) {
      this.email = email;
      정 anyValueUpdated = true;
    }
    if (passwrod != null && !passwrod.isBlank()) {
      this.password = passwrod;
      anyValueUpdated = true;
    }
    if (anyValueUpdated) {
      updateTimestamp();
    }
  }
}
