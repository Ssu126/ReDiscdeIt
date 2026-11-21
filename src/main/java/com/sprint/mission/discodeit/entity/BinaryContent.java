package com.sprint.mission.discodeit.entity;

import lombok.Getter;

@Getter
public class BinaryContent extends Common {

  private static final long serialVersionUID = 1L;

  private final String fileName;
  private final String contentType;
  private final Long size;
  private final byte[] bytes;

  public BinaryContent(String fileName, String contentType, byte[] bytes) {
    this.fileName = fileName;
    this.contentType = contentType;
    this.size = (long) bytes.length;
    this.bytes = bytes;
  }
}
