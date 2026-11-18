package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(
    name = "discodeit.repository.type",
    havingValue = "jcf",
    matchIfMissing = true)
public class JCFUserStatusRepository extends JCFRepository<UserStatus>
    implements UserStatusRepository {}
