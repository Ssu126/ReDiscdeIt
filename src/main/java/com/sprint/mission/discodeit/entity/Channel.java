package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import lombok.Getter;

@Getter
public class Channel extends BaseUpdatableEntity {

  private String name;
  private String description;
  private ChannelType type;

  public Channel(String name, String description, ChannelType type) {
    this.name = name;
    this.description = description;
    this.type = type;
  }

  public void update(String name, String description) {
    if (name != null && !name.isBlank()) {
      this.name = name;
    }
    if (description != null && !description.isBlank()) {
      this.description = description;
    }
  }
}
