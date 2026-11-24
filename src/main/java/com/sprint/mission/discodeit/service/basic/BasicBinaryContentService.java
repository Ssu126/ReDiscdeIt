package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.response.BinaryContentResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicBinaryContentService implements BinaryContentService {

  private final BinaryContentRepository binaryContentRepository;

  @Override
  @Transactional
  public BinaryContentResponse create(BinaryContentCreateRequest request) {
    BinaryContent binaryContent = binaryContentRepository.save(request.toEntity());

    return BinaryContentResponse.toResponse(binaryContent);
  }

  @Override
  public BinaryContentResponse find(UUID id) {
    return BinaryContentResponse.toResponse(findBinaryContentOrThrow(id));
  }

  @Override
  public List<BinaryContentResponse> findAllByIdIn(List<UUID> ids) {
    return binaryContentRepository.findAllById(ids).stream()
        .map(BinaryContentResponse::toResponse)
        .toList();
  }

  @Override
  @Transactional
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
