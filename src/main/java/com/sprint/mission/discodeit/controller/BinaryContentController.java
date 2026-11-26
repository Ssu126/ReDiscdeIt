package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.response.BinaryContentDto;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/binaryContents")
public class BinaryContentController {

  private final BinaryContentService binaryContentService;
  private final BinaryContentStorage binaryContentStorage;

  @RequestMapping(value = "/{binaryContentId}", method = RequestMethod.GET)
  public ResponseEntity<BinaryContentDto> find(@PathVariable UUID binaryContentId) {
    return ResponseEntity.ok(binaryContentService.find(binaryContentId));
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<BinaryContentDto>> findAllByIdIn(
      @RequestParam("binaryContentIds") List<UUID> binaryContentIds) {
    return ResponseEntity.ok(binaryContentService.findAllByIdIn(binaryContentIds));
  }

  @RequestMapping(value = "/{binaryContentId}/download", method = RequestMethod.GET)
  public ResponseEntity<?> download(@PathVariable UUID binaryContentId) {
    BinaryContentDto binaryContentDto = binaryContentService.find(binaryContentId);
    return binaryContentStorage.download(binaryContentDto);
  }
}
