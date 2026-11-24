package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.MessageResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;
  private final BinaryContentRepository binaryContentRepository;

  @Override
  @Transactional
  public MessageResponse create(
      MessageCreateRequest messageCreateRequest, List<BinaryContentCreateRequest> attachments) {
    User author = findUserOrThrow(messageCreateRequest.authorId());
    Channel channel = findChannelOrThrow(messageCreateRequest.channelId());
    validateContent(messageCreateRequest.content());

    List<BinaryContent> attachmentContents =
        attachments.stream().map(BinaryContentCreateRequest::toEntity).toList();

    Message message =
        messageRepository.save(
            new Message(messageCreateRequest.content(), author, channel, attachmentContents));

    return MessageResponse.toResponse(message);
  }

  @Override
  public MessageResponse find(UUID id) {
    Message message = findMessageOrThrow(id);
    return MessageResponse.toResponse(message);
  }

  @Override
  public List<MessageResponse> findAllByChannelId(UUID channelId) {
    return messageRepository.findAllByChannelId(channelId).stream()
        .map(MessageResponse::toResponse)
        .toList();
  }

  @Override
  @Transactional
  public MessageResponse update(UUID id, MessageUpdateRequest request) {
    Message message = findMessageOrThrow(id);
    validateContent(request.newContent());

    message.update(request.newContent());

    return MessageResponse.toResponse(message);
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    findMessageOrThrow(id);
    messageRepository.deleteById(id);
  }

  private User findUserOrThrow(UUID userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 id: " + userId));
  }

  private Message findMessageOrThrow(UUID id) {
    return messageRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메시지 id: " + id));
  }

  private Channel findChannelOrThrow(UUID channelId) {
    return channelRepository
        .findById(channelId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 채널 id: " + channelId));
  }

  private void validateContent(String content) {
    if (content == null || content.isBlank()) {
      throw new IllegalArgumentException("메시지 내용이 비어있습니다.");
    }
  }
}
