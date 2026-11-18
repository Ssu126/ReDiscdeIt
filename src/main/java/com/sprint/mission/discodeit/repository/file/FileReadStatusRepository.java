package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
public class FileReadStatusRepository extends FileRepository<ReadStatus>
    implements ReadStatusRepository {

  public FileReadStatusRepository(
      @Value("${discodeit.repository.file-directory:.discodeit}") String fileDirectory) {
    super(fileDirectory, "readStatuses", ReadStatus.class);
  }
}
