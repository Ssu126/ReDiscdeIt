package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import java.util.UUID;

public interface MessageRepository extends CrudRepository<Message> {

  default List<Message> findAllByChannelId(UUID channelId) {
    return findAll().stream().filter(message -> message.getChannelId().equals(channelId)).toList();
  }

  default void deleteByChannelId(UUID channelId) {
    findAllByChannelId(channelId).forEach(message -> deleteById(message.getId()));
  }
}
