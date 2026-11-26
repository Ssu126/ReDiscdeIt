package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.response.BinaryContentDto;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
// yaml에서 discodeit.storage.type이 local일 때만 Bean으로 등록
@ConditionalOnProperty(name = "discodeit.storage.type", havingValue = "local")
public class LocalBinaryContentStorage implements BinaryContentStorage {

  private final Path root;

  public LocalBinaryContentStorage(@Value("${discodeit.storage.local.root-path}") String rootPath) {
    this.root = Paths.get(rootPath);
  }

  // Bean 생성 시 자동 호출
  @PostConstruct
  void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new UncheckedIOException("저장소 루트 디렉토리를 만들 수 없습니다: " + root, e);
    }
  }

  @Override
  public UUID put(UUID id, byte[] bytes) {
    try {
      Files.write(resolvePath(id), bytes);
    } catch (IOException e) {
      throw new UncheckedIOException("파일 저장에 실패했습니다: " + id, e);
    }
    return id;
  }

  @Override
  public InputStream get(UUID id) {
    Path path = resolvePath(id);
    if (Files.notExists(path)) {
      throw new NoSuchElementException("저장된 파일이 없습니다: " + id);
    }
    try {
      return Files.newInputStream(path);
    } catch (IOException e) {
      throw new UncheckedIOException("파일을 읽을 수 없습니다: " + id, e);
    }
  }

  @Override
  public ResponseEntity<Resource> download(BinaryContentDto binaryContentDto) {
    Resource resource = new InputStreamResource(get(binaryContentDto.id()));
    ContentDisposition disposition =
        ContentDisposition.attachment()
            // 한글 파일명이 다운로드할 때 깨지지 않게 만든다
            .filename(binaryContentDto.fileName(), StandardCharsets.UTF_8)
            .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
        .contentType(MediaType.parseMediaType(binaryContentDto.contentType()))
        .contentLength(binaryContentDto.size())
        .body(resource);
  }

  private Path resolvePath(UUID id) {
    return root.resolve(id.toString());
  }
}
