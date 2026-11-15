package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
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

public class FileUserService implements UserService {

  private static final String EXTENSION = ".ser";
  private final Path directory;

  // 경로 지정
  public FileUserService() {
    this(Paths.get(System.getProperty("user.dir"), "data", "users"));
  }

  // 디렉토리 초기화 및 생성
  public FileUserService(Path directory) {
    this.directory = directory;

    try {
      Files.createDirectories(directory);
    } catch (IOException e) {
      throw new UncheckedIOException("디렉토리를 생성할 수 없습니다: " + directory, e);
    }
  }

  @Override
  public User create(String username, String email, String password) {
    User user = new User(username, email, password);
    save(user);
    return user;
  }

  @Override
  public User find(UUID id) {
    return findByIdOrThrow(id);
  }

  @Override
  public List<User> findAll() {
    try (Stream<Path> paths = Files.list(directory)) {
      // EXTENSION(.ser)로 끝나는 경로의 파일들을 read(자바 객체로 복원)
      return paths.filter(path -> path.toString().endsWith(EXTENSION)).map(this::read).toList();
    } catch (IOException e) {
      throw new UncheckedIOException("유저 목록 조회 실패: " + directory, e);
    }
  }

  @Override
  public User update(UUID id, String username, String email, String password) {
    User user = findByIdOrThrow(id);
    user.update(username, email, password);
    save(user);
    return user;
  }

  @Override
  public void delete(UUID id) {
    findByIdOrThrow(id);
    try {
      // 경로 파일 삭제
      Files.deleteIfExists(resolvePath(id));
    } catch (IOException e) {
      throw new UncheckedIOException("유저 삭제 실패: " + id, e);
    }
  }

  // 유저 예외 처리
  private User findByIdOrThrow(UUID id) {
    User user = load(id);
    if (user == null) {
      throw new NoSuchElementException("존재하지 않는 유저 id: " + id);
    }
    return user;
  }

  // 유저를 저장할 파일의 전체 경로
  private Path resolvePath(UUID id) {
    return directory.resolve(id + EXTENSION);
  }

  private void save(User user) {
    Path path = resolvePath(user.getId());
    // 파일 출력 스트을 열고, 바이트 스트림로 감싸 바이트 단위로 직렬화
    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
      // 실제 파일에 기록
      oos.writeObject(user);
    } catch (IOException e) {
      throw new UncheckedIOException("유저 저장 실패: " + path, e);
    }
  }

  // 경로 예외 처리
  private User load(UUID id) {
    Path path = resolvePath(id);
    if (Files.notExists(path)) {
      return null;
    }
    return read(path);
  }

  private User read(Path path) {
    // 파일 입력 스트림을 열고, 바이트 데이터를 객체 형태로 역직렬화
    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
      // 데이터를 읽어 자바 객체 인스턴스로 복원, 오브젝트로 반환
      return (User) ois.readObject();
    } catch (IOException e) {
      throw new UncheckedIOException("읽기 실패: " + path, e);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("역직렬화 실패: " + path, e);
    }
  }
}
