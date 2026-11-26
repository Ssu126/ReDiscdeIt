package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.dto.response.UserDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

  UserDto create(UserCreateRequest createRequest, Optional<BinaryContentCreateRequest> profile);

  UserDto find(UUID id);

  List<UserDto> findAll();

  UserDto update(
      UUID id, UserUpdateRequest updateRequest, Optional<BinaryContentCreateRequest> profile);

  void delete(UUID id);
}
