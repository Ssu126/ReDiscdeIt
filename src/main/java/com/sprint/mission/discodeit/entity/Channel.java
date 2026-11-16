package com.sprint.mission.discodeit.entity;

import lombok.Getter;

@Getter
public class Channel extends BaseUpdatableEntity {

  private static final long serialVersionUID = 1L;

  private String name;
  private String description;
  private ChannelType type;

  public Channel(String name, String description, ChannelType type) {
    this.name = name;
    this.description = description;
    this.type = type;
  }

  public void update(String name, String description) {
    boolean anyValueUpdated = false;
    if (name != null && !name.isBlank()) {
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
