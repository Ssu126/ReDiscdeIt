package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.base.BaseEntity;
import com.sprint.mission.discodeit.repository.CrudRepository;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public abstract class FileRepository<T extends BaseEntity> implements CrudRepository<T> {

  private static final String EXTENSION = ".ser";
  private static final Map<Path, ReentrantLock> LOCKS = new ConcurrentHashMap<>();
  private final Path directory;
  private final Class<T> type;

  protected FileRepository(String fileDirectory, String directoryName, Class<T> type) {
    this.directory = Paths.get(fileDirectory, directoryName);
    this.type = type;

    try {
      Files.createDirectories(directory);
    } catch (IOException e) {
      throw new UncheckedIOException("디렉토리를 생성할 수 없습니다: " + directory, e);
    }
  }

  @Override
  public T save(T entity) {
    Path path = resolvePath(entity.getId());
    ReentrantLock lock = lockOf(path);
    lock.lock();
    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
      oos.writeObject(entity);
      return entity;
    } catch (IOException e) {
      throw new UncheckedIOException("저장 실패: " + path, e);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public Optional<T> findById(UUID id) {
    Path path = resolvePath(id);
    if (Files.notExists(path)) {
      return Optional.empty();
    }
    return Optional.of(read(path));
  }

  @Override
  public List<T> findAll() {
    try (Stream<Path> paths = Files.list(directory)) {
      return paths.filter(path -> path.toString().endsWith(EXTENSION)).map(this::read).toList();
    } catch (IOException e) {
      throw new UncheckedIOException("목록 조회 실패: " + directory, e);
    }
  }

  @Override
  public boolean existsById(UUID id) {
    return Files.exists(resolvePath(id));
  }

  @Override
  public void deleteById(UUID id) {
    Path path = resolvePath(id);
    ReentrantLock lock = lockOf(path);
    lock.lock();
    try {
      Files.deleteIfExists(path);
    } catch (IOException e) {
      throw new UncheckedIOException("삭제 실패: " + id, e);
    } finally {
      lock.unlock();
    }
  }

  private Path resolvePath(UUID id) {
    return directory.resolve(id + EXTENSION);
  }

  // EOFException이 날 수 있어 파일별로 잠근다.
  private ReentrantLock lockOf(Path path) {
    return LOCKS.computeIfAbsent(path, k -> new ReentrantLock());
  }

  private T read(Path path) {
    ReentrantLock lock = lockOf(path);
    lock.lock();
    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
      return type.cast(ois.readObject());
    } catch (IOException e) {
      throw new UncheckedIOException("읽기 실패: " + path, e);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("역직렬화 실패: " + path, e);
    } finally {
      lock.unlock();
    }
  }
}
