package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.dto.response.UserResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

  UserResponse create(
      UserCreateRequest createRequest, Optional<BinaryContentCreateRequest> profile);

  UserResponse find(UUID id);

  List<UserResponse> findAll();

  UserResponse update(
      UserUpdateRequest updateRequest, Optional<BinaryContentCreateRequest> profile);

  void delete(UUID id);
}
