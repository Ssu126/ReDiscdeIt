package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.MessageResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
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

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;
  private final BinaryContentRepository binaryContentRepository;

  @Override
  public MessageResponse create(
      MessageCreateRequest messageCreateRequest, List<BinaryContentCreateRequest> attachments) {
    validateUserExists(messageCreateRequest.authorId());
    validateChannelExists(messageCreateRequest.channelId());
    validateContent(messageCreateRequest.content());

    List<UUID> attachmentIds =
        attachments.stream()
            .map(
                attachment ->
                    binaryContentRepository
                        .save(
                            new BinaryContent(
                                attachment.filename(),
                                attachment.contentType(),
                                attachment.bytes()))
                        .getId())
            .toList();

    Message message =
        messageRepository.save(
            new Message(
                messageCreateRequest.content(),
                messageCreateRequest.authorId(),
                messageCreateRequest.channelId(),
                attachmentIds));

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
  public MessageResponse update(MessageUpdateRequest request) {
    Message message = findMessageOrThrow(request.id());
    validateContent(request.content());

    message.update(request.content());
    messageRepository.save(message);

    return MessageResponse.toResponse(message);
  }

  @Override
  public void delete(UUID id) {
    Message message = findMessageOrThrow(id);

    message.getAttachmentIds().forEach(binaryContentRepository::deleteById);
    messageRepository.deleteById(id);
  }

  private Message findMessageOrThrow(UUID id) {
    return messageRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메시지 id: " + id));
  }

  private void validateUserExists(UUID userId) {
    userRepository
        .findById(userId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 id: " + userId));
  }

  private void validateChannelExists(UUID channelId) {
    channelRepository
        .findById(channelId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 채널 id: " + channelId));
  }

  private void validateContent(String content) {
    if (content == null || content.isBlank()) {
      throw new IllegalArgumentException("메시지 내용이 비어있습니다.");
    }
  }
}
