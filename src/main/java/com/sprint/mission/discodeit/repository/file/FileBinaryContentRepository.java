package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "file")
public class FileBinaryContentRepository extends FileRepository<BinaryContent>
    implements BinaryContentRepository {

  public FileBinaryContentRepository(
      @Value("${discodeit.repository.file-directory:.discodeit}") String fileDirectory) {
    super(fileDirectory, "binaryContents", BinaryContent.class);
  }
}
