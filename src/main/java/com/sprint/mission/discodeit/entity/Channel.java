package com.sprint.mission.discodeit.entity;

public class Channel extends Common {

  private String name;
  private String description;

  public Channel(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void update(String name, String description) {
    boolean anyValueUpdated = false;
    if (name != null && !description.isBlank()) {
      this.name = name;
      anyValueUpdated = true;
    }
    if (description != null && !description.isBlank()) {
      this.description = description;
      anyValueUpdated = true;
    }
    if (anyValueUpdated) {
      updateTimestamp();
    }
  }
}
