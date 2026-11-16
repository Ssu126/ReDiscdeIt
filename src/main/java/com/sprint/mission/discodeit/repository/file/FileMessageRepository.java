package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

public class FileMessageRepository extends FileRepository<Message> implements MessageRepository {

  public FileMessageRepository() {
    super("messages", Message.class);
  }
}
