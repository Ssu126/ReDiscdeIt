package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JCFUserStatusRepository extends JCFRepository<UserStatus>
    implements UserStatusRepository {}
