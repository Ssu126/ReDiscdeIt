package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;
import java.util.List;
import java.util.UUID;

public interface ReadStatusRepository extends CrudRepository<ReadStatus> {

  default List<ReadStatus> findAllByChannelId(UUID channelId) {
    return findAll().stream()
        .filter(readStatus -> readStatus.getChannel().getId().equals(channelId))
        .toList();
  }

  default List<ReadStatus> findAllByUserId(UUID userId) {
    return findAll().stream()
        .filter(readStatus -> readStatus.getUser().getId().equals(userId))
        .toList();
  }

  default void deleteByChannelId(UUID channelId) {
    findAllByChannelId(channelId).forEach(readStatus -> deleteById(readStatus.getId()));
  }

  default boolean existsByUserIdAndChannelId(UUID userId, UUID channelId) {
    return findAll().stream()
        .anyMatch(
            readStatus ->
                readStatus.getUser().getId().equals(userId)
                    && readStatus.getChannel().getId().equals(channelId));
  }
}
