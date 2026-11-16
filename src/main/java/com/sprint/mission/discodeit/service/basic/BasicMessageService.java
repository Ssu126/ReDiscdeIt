package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;

  @Override
  public Message create(String content, UUID authorId, UUID channelId) {
    userRepository
        .findById(authorId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 id: " + authorId));
    channelRepository
        .findById(channelId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 채널 id: " + channelId));
    validateContent(content);

    Message message = new Message(content, authorId, channelId);
    return messageRepository.save(message);
  }

  @Override
  public Message find(UUID id) {
    return findMessageOrThrow(id);
  }

  @Override
  public List<Message> findAll() {
    return messageRepository.findAll();
  }

  @Override
  public Message update(UUID id, String content) {
    Message message = findMessageOrThrow(id);
    validateContent(content);

    message.update(content);
    return messageRepository.save(message);
  }

  @Override
  public void delete(UUID id) {
    findMessageOrThrow(id);
    messageRepository.deleteById(id);
  }

  private Message findMessageOrThrow(UUID id) {
    return messageRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메시지 id: " + id));
  }

  private void validateContent(String content) {
    if (content == null || content.isBlank()) {
      throw new IllegalArgumentException("메시지 내용이 비어있습니다.");
    }
  }
}
