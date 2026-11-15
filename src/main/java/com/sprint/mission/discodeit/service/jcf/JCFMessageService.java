package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class JCFMessageService implements MessageService {

  private final Map<UUID, Message> data;
  private final UserService userService;
  private final ChannelService channelService;

  public JCFMessageService(UserService userService, ChannelService channelService) {
    data = new HashMap<>();
    this.userService = userService;
    this.channelService = channelService;
  }

  @Override
  public Message create(String content, UUID authorId, UUID channelId) {
    userService.find(authorId);
    channelService.find(channelId);
    validateContent(content);

    Message message = new Message(content, authorId, channelId);
    data.put(message.getId(), message);
    return data.get(message.getId());
  }

  @Override
  public Message find(UUID id) {
    return findByIdOrThrow(id);
  }


  @Override
  public List<Message> findAll() {
    return new ArrayList<>(data.values());
  }

  @Override
  public Message update(UUID id, String content) {
    Message message = findByIdOrThrow(id);
    validateContent(content);

    message.update(content);
    return message;
  }

  @Override
  public void delete(UUID id) {
    findByIdOrThrow(id);
    data.remove(id);
  }

  private Message findByIdOrThrow(UUID id) {
    Message message = data.get(id);
    if (message == null) {
      throw new NoSuchElementException("존재하지 않는 메시지 id: " + id);
    }
    return message;
  }

  private void validateContent(String content) {
    if (content == null || content.isBlank()) {
      throw new IllegalArgumentException("메시지 내용이 비어있습니다.");
    }
  }
}
