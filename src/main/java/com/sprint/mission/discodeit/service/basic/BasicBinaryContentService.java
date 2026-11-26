package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.response.BinaryContentDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
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
  private final BinaryContentStorage binaryContentStorage;
  private final BinaryContentMapper binaryContentMapper;

  @Override
  @Transactional
  public BinaryContentDto create(BinaryContentCreateRequest request) {
    BinaryContent binaryContent = binaryContentRepository.save(request.toEntity());
    binaryContentStorage.put(binaryContent.getId(), request.bytes());

    return binaryContentMapper.toDto(binaryContent);
  }

  @Override
  public BinaryContentDto find(UUID id) {
    return binaryContentMapper.toDto(findBinaryContentOrThrow(id));
  }

  @Override
  public List<BinaryContentDto> findAllByIdIn(List<UUID> ids) {
    return binaryContentRepository.findAllById(ids).stream()
        .map(binaryContentMapper::toDto)
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
