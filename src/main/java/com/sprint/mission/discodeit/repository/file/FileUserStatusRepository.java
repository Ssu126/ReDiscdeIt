package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FileUserStatusRepository extends FileRepository<UserStatus>
    implements UserStatusRepository {

  public FileUserStatusRepository() {
    super("userStatuses", UserStatus.class);
  }
}
