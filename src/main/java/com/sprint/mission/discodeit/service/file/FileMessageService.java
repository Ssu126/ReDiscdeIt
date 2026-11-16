package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

public class FileMessageService implements MessageService {

  private static final String EXTENSION = ".ser";
  private final Path directory;
  private final UserService userService;
  private final ChannelService channelService;

  public FileMessageService(UserService userService, ChannelService channelService) {
    this(
        Paths.get(System.getProperty("user.dir"), "data", "messages"), userService, channelService);
  }

  public FileMessageService(
      Path directory, UserService userService, ChannelService channelService) {
    this.directory = directory;
    this.userService = userService;
    this.channelService = channelService;

    try {
      Files.createDirectories(directory);
    } catch (IOException e) {
      throw new UncheckedIOException("디렉토리를 생성할 수 없습니다: " + directory, e);
    }
  }

  @Override
  public Message create(String content, UUID authorId, UUID channelId) {
    userService.find(authorId);
    channelService.find(channelId);
    validateContent(content);

    Message message = new Message(content, authorId, channelId);
    save(message);
    return message;
  }

  @Override
  public Message find(UUID id) {
    return findByIdOrThrow(id);
  }

  @Override
  public List<Message> findAll() {
    try (Stream<Path> paths = Files.list(directory)) {
      return paths.filter(path -> path.toString().endsWith(EXTENSION)).map(this::read).toList();
    } catch (IOException e) {
      throw new UncheckedIOException("메시지 목록 조회 실패: " + directory, e);
    }
  }

  @Override
  public Message update(UUID id, String content) {
    Message message = findByIdOrThrow(id);
    validateContent(content);
    message.update(content);
    save(message);
    return message;
  }

  @Override
  public void delete(UUID id) {
    findByIdOrThrow(id);
    try {
      Files.deleteIfExists(resolvePath(id));
    } catch (IOException e) {
      throw new UncheckedIOException("메시지 삭제 실패: " + id, e);
    }
  }

  private Message findByIdOrThrow(UUID id) {
    Message message = load(id);
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

  private Path resolvePath(UUID id) {
    return directory.resolve(id + EXTENSION);
  }

  private void save(Message message) {
    Path path = resolvePath(message.getId());
    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
      oos.writeObject(message);
    } catch (IOException e) {
      throw new UncheckedIOException("메시지 저장 실패: " + path, e);
    }
  }

  private Message load(UUID id) {
    Path path = resolvePath(id);
    if (Files.notExists(path)) {
      return null;
    }
    return read(path);
  }

  private Message read(Path path) {
    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
      return (Message) ois.readObject();
    } catch (IOException e) {
      throw new UncheckedIOException("메시지 읽기 실패: " + path, e);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("메시지 역직렬화 실패: " + path, e);
    }
  }
}
