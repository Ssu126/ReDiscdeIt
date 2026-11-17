package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JCFBinaryContentRepository extends JCFRepository<BinaryContent> implements
    BinaryContentRepository {

}
