package com.sprint.mission.discodeit.entity;

import lombok.Getter;

@Getter
public class BinaryContent extends Common {

  private static final long serialVersionUID = 1L;

  private final String filename;
  private final String contentType;
  private final Long size;
  private final byte[] bytes;

  public BinaryContent(String filename, String contentType, byte[] bytes) {
    this.filename = filename;
    this.contentType = contentType;
    this.size = (long) bytes.length;
    this.bytes = bytes;
  }
}
