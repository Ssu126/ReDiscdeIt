package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;
import java.util.List;
import java.util.UUID;

public interface ReadStatusRepository extends CrudRepository<ReadStatus> {

  default List<ReadStatus> findAllByChannelId(UUID channelId) {
    return findAll().stream()
        .filter(readStatus -> readStatus.getChannelId().equals(channelId))
        .toList();
  }

  default List<ReadStatus> findAllByUserId(UUID userId) {
    return findAll().stream().filter(readStatus -> readStatus.getUserId().equals(userId)).toList();
  }

  default void deleteByChannelId(UUID channelId) {
    findAllByChannelId(channelId).forEach(readStatus -> deleteById(readStatus.getId()));
  }
}
