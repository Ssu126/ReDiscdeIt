package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
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

public class FileChannelService implements ChannelService {

  private static final String EXTENSION = ".ser";
  private final Path directory;

  public FileChannelService() {
    this(Paths.get(System.getProperty("user.dir"), "data", "channels"));
  }

  public FileChannelService(Path directory) {
    this.directory = directory;

    try {
      Files.createDirectories(directory);
    } catch (IOException e) {
      throw new UncheckedIOException("디렉토리를 생성할 수 없습니다: " + directory, e);
    }
  }

  @Override
  public Channel create(String name, String description) {
    Channel channel = new Channel(name, description);
    save(channel);
    return channel;
  }

  @Override
  public Channel find(UUID id) {
    return findByIdOrThrow(id);
  }

  @Override
  public List<Channel> findAll() {
    try (Stream<Path> paths = Files.list(directory)) {
      return paths.filter(path -> path.toString().endsWith(EXTENSION)).map(this::read).toList();
    } catch (IOException e) {
      throw new UncheckedIOException("채널 목록 조회 실패: " + directory, e);
    }
  }

  @Override
  public Channel update(UUID id, String name, String description) {
    Channel channel = findByIdOrThrow(id);
    channel.update(name, description);
    save(channel);
    return channel;
  }

  @Override
  public void delete(UUID id) {
    findByIdOrThrow(id);
    try {
      Files.deleteIfExists(resolvePath(id));
    } catch (IOException e) {
      throw new UncheckedIOException("채널 삭제 실패: " + id, e);
    }
  }

  private Channel findByIdOrThrow(UUID id) {
    Channel channel = load(id);
    if (channel == null) {
      throw new NoSuchElementException("존재하지 않는 채널 id: " + id);
    }
    return channel;
  }

  private Path resolvePath(UUID id) {
    return directory.resolve(id + EXTENSION);
  }

  private void save(Channel channel) {
    Path path = resolvePath(channel.getId());
    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
      oos.writeObject(channel);
    } catch (IOException e) {
      throw new UncheckedIOException("채널 저장 실패: " + path, e);
    }
  }

  private Channel load(UUID id) {
    Path path = resolvePath(id);
    if (Files.notExists(path)) {
      return null;
    }
    return read(path);
  }

  private Channel read(Path path) {
    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
      return (Channel) ois.readObject();
    } catch (IOException e) {
      throw new UncheckedIOException("채널 읽기 실패: " + path, e);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("채널 역직렬화 실패: " + path, e);
    }
  }
}
