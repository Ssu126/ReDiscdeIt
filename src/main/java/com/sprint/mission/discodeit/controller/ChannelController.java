package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.response.ChannelResponse;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels")
public class ChannelController {

  private final ChannelService channelService;

  @RequestMapping(value = "/public", method = RequestMethod.POST)
  public ResponseEntity<ChannelResponse> createPublic(
      @RequestBody PublicChannelCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(channelService.createPublic(request));
  }

  @RequestMapping(value = "/private", method = RequestMethod.POST)
  public ResponseEntity<ChannelResponse> createPrivate(
      @RequestBody PrivateChannelCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(channelService.createPrivate(request));
  }

  @RequestMapping(value = "/{channelId}", method = RequestMethod.PATCH)
  public ResponseEntity<ChannelResponse> update(
      @PathVariable UUID channelId, @RequestBody ChannelUpdateRequest request) {
    return ResponseEntity.ok(channelService.update(channelId, request));
  }

  @RequestMapping(value = "/{channelId}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable UUID channelId) {
    channelService.delete(channelId);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<ChannelResponse>> findAllByUserId(@RequestParam UUID userId) {
    return ResponseEntity.ok(channelService.findAllByUserId(userId));
  }
}
