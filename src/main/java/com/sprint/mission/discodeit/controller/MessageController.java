package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.MessageResponse;
import com.sprint.mission.discodeit.service.MessageService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageService messageService;

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<MessageResponse> create(
      @RequestPart("messageCreateRequest") MessageCreateRequest request,
      @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments)
      throws IOException {
    MessageResponse created = messageService.create(request, toAttachmentRequests(attachments));
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @RequestMapping(value = "/{messageId}", method = RequestMethod.PATCH)
  public ResponseEntity<MessageResponse> update(
      @PathVariable UUID messageId, @RequestBody MessageUpdateRequest request) {
    return ResponseEntity.ok(messageService.update(messageId, request));
  }

  @RequestMapping(value = "/{messageId}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable UUID messageId) {
    messageService.delete(messageId);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<MessageResponse>> findAllByChannelId(@RequestParam UUID channelId) {
    return ResponseEntity.ok(messageService.findAllByChannelId(channelId));
  }

  private List<BinaryContentCreateRequest> toAttachmentRequests(List<MultipartFile> files)
      throws IOException {
    List<BinaryContentCreateRequest> requests = new ArrayList<>();
    if (files == null) {
      return requests;
    }
    for (MultipartFile file : files) {
      if (!file.isEmpty()) {
        requests.add(
            new BinaryContentCreateRequest(
                file.getOriginalFilename(), file.getContentType(), file.getBytes()));
      }
    }
    return requests;
  }
}
