package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.response.BinaryContentResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {

  private final BinaryContentRepository binaryContentRepository;

  @Override
  public BinaryContentResponse create(BinaryContentCreateRequest request) {
    BinaryContent binaryContent =
        binaryContentRepository.save(
            new BinaryContent(request.filename(), request.contentType(), request.bytes()));

    return BinaryContentResponse.toResponse(binaryContent);
  }

  @Override
  public BinaryContentResponse find(UUID id) {
    return BinaryContentResponse.toResponse(findBinaryContentOrThrow(id));
  }

  @Override
  public List<BinaryContentResponse> findAllByIdIn(List<UUID> ids) {
    return ids.stream()
        .map(binaryContentRepository::findById)
        .flatMap(Optional::stream)
        .map(BinaryContentResponse::toResponse)
        .toList();
  }

  @Override
  public void delete(UUID id) {
    findBinaryContentOrThrow(id);
    binaryContentRepository.deleteById(id);
  }

  private BinaryContent findBinaryContentOrThrow(UUID id) {
    return binaryContentRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 파일 id: " + id));
  }
}
