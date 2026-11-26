# API 변경 내역 v0 vs. n/a

## API 변경 사항

### POST /api/auth/login

- API 태그 `Auth` 추가됨
- API 태그 `auth-controller` 제거됨
- 상태 코드 `400` 실패 응답 추가됨
- 상태 코드 `404` 실패 응답 추가됨
- `200` 응답에 선택 속성 `profile` 추가됨
- `200` 응답에서 선택 속성 `createdAt` 제거됨
- `200` 응답에서 선택 속성 `profileId` 제거됨
- `200` 응답에서 선택 속성 `updatedAt` 제거됨

### GET /api/binaryContents

- API 태그 `BinaryContent` 추가됨
- API 태그 `binary-content-controller` 제거됨
- `200` 응답에서 선택 속성 `items/bytes` 제거됨
- `200` 응답에서 선택 속성 `items/createdAt` 제거됨

### GET /api/binaryContents/{binaryContentId}

- API 태그 `BinaryContent` 추가됨
- API 태그 `binary-content-controller` 제거됨
- 상태 코드 `404` 실패 응답 추가됨
- `200` 응답에서 선택 속성 `bytes` 제거됨
- `200` 응답에서 선택 속성 `createdAt` 제거됨

### GET /api/binaryContents/{binaryContentId}/download

- 엔드포인트 추가됨

### GET /api/channels

- API operation id `findAllByUserId_1`이 제거되고 `findAll_1`로 대체됨
- API 태그 `Channel` 추가됨
- API 태그 `channel-controller` 제거됨
- `200` 응답에 선택 속성 `items/participants` 추가됨
- `200` 응답에서 선택 속성 `items/createdAt` 제거됨
- `200` 응답에서 선택 속성 `items/participantIds` 제거됨
- `200` 응답에서 선택 속성 `items/updatedAt` 제거됨

### POST /api/channels/private

- ⚠️ 상태 코드 `200` 성공 응답 제거됨
- API operation id `createPrivate`가 제거되고 `create_4`로 대체됨
- API 태그 `Channel` 추가됨
- API 태그 `channel-controller` 제거됨
- 상태 코드 `201` 성공 응답 추가됨

### POST /api/channels/public

- ⚠️ 상태 코드 `200` 성공 응답 제거됨
- API operation id `createPublic`이 제거되고 `create_3`으로 대체됨
- API 태그 `Channel` 추가됨
- API 태그 `channel-controller` 제거됨
- 상태 코드 `201` 성공 응답 추가됨

### DELETE /api/channels/{channelId}

- ⚠️ 상태 코드 `200` 성공 응답 제거됨
- API 태그 `Channel` 추가됨
- API 태그 `channel-controller` 제거됨
- 상태 코드 `404` 실패 응답 추가됨
- 상태 코드 `204` 성공 응답 추가됨

### PATCH /api/channels/{channelId}

- API 태그 `Channel` 추가됨
- API 태그 `channel-controller` 제거됨
- 상태 코드 `400` 실패 응답 추가됨
- 상태 코드 `404` 실패 응답 추가됨
- `200` 응답에 선택 속성 `participants` 추가됨
- `200` 응답에서 선택 속성 `createdAt` 제거됨
- `200` 응답에서 선택 속성 `participantIds` 제거됨
- `200` 응답에서 선택 속성 `updatedAt` 제거됨

### GET /api/messages

- ⚠️ 필수 `query` 요청 파라미터 `pageable` 새로 추가됨
- ⚠️ 상태 코드 `200` 응답 본문의 `type`이 `array<object>`에서 `object`로 변경됨
- API 태그 `Message` 추가됨
- API 태그 `message-controller` 제거됨
- `200` 응답에 선택 속성 `content` 추가됨
- `200` 응답에 선택 속성 `hasNext` 추가됨
- `200` 응답에 선택 속성 `number` 추가됨
- `200` 응답에 선택 속성 `size` 추가됨
- `200` 응답에 선택 속성 `totalElements` 추가됨

### POST /api/messages

- ⚠️ 상태 코드 `200` 성공 응답 제거됨
- API 태그 `Message` 추가됨
- API 태그 `message-controller` 제거됨
- 상태 코드 `404` 실패 응답 추가됨
- 상태 코드 `201` 성공 응답 추가됨

### DELETE /api/messages/{messageId}

- ⚠️ 상태 코드 `200` 성공 응답 제거됨
- API 태그 `Message` 추가됨
- API 태그 `message-controller` 제거됨
- 상태 코드 `404` 실패 응답 추가됨
- 상태 코드 `204` 성공 응답 추가됨

### PATCH /api/messages/{messageId}

- API 태그 `Message` 추가됨
- API 태그 `message-controller` 제거됨
- 상태 코드 `404` 실패 응답 추가됨
- `200` 응답에 선택 속성 `attachments` 추가됨
- `200` 응답에 선택 속성 `author` 추가됨
- `200` 응답에서 선택 속성 `attachmentIds` 제거됨
- `200` 응답에서 선택 속성 `authorId` 제거됨

### GET /api/readStatuses

- API 태그 `ReadStatus` 추가됨
- API 태그 `read-status-controller` 제거됨
- `200` 응답에서 선택 속성 `items/createdAt` 제거됨
- `200` 응답에서 선택 속성 `items/updatedAt` 제거됨

### POST /api/readStatuses

- ⚠️ 상태 코드 `200` 성공 응답 제거됨
- API 태그 `ReadStatus` 추가됨
- API 태그 `read-status-controller` 제거됨
- 상태 코드 `400` 실패 응답 추가됨
- 상태 코드 `404` 실패 응답 추가됨
- 상태 코드 `201` 성공 응답 추가됨

### PATCH /api/readStatuses/{readStatusId}

- API 태그 `ReadStatus` 추가됨
- API 태그 `read-status-controller` 제거됨
- 상태 코드 `404` 실패 응답 추가됨
- `200` 응답에서 선택 속성 `createdAt` 제거됨
- `200` 응답에서 선택 속성 `updatedAt` 제거됨

### GET /api/users

- API 태그 `User` 추가됨
- API 태그 `user-controller` 제거됨
- `200` 응답에 선택 속성 `items/profile` 추가됨
- `200` 응답에서 선택 속성 `items/createdAt` 제거됨
- `200` 응답에서 선택 속성 `items/profileId` 제거됨
- `200` 응답에서 선택 속성 `items/updatedAt` 제거됨

### POST /api/users

- ⚠️ 상태 코드 `200` 성공 응답 제거됨
- API 태그 `User` 추가됨
- API 태그 `user-controller` 제거됨
- 상태 코드 `400` 실패 응답 추가됨
- 상태 코드 `201` 성공 응답 추가됨

### DELETE /api/users/{userId}

- ⚠️ 상태 코드 `200` 성공 응답 제거됨
- API 태그 `User` 추가됨
- API 태그 `user-controller` 제거됨
- 상태 코드 `404` 실패 응답 추가됨
- 상태 코드 `204` 성공 응답 추가됨

### PATCH /api/users/{userId}

- API 태그 `User` 추가됨
- API 태그 `user-controller` 제거됨
- 상태 코드 `400` 실패 응답 추가됨
- 상태 코드 `404` 실패 응답 추가됨
- `200` 응답에 선택 속성 `profile` 추가됨
- `200` 응답에서 선택 속성 `createdAt` 제거됨
- `200` 응답에서 선택 속성 `profileId` 제거됨
- `200` 응답에서 선택 속성 `updatedAt` 제거됨

### PATCH /api/users/{userId}/userStatus

- API operation id `updateUserStatus`가 제거되고 `updateUserStatusByUserId`로 대체됨
- API 태그 `User` 추가됨
- API 태그 `user-controller` 제거됨
- 상태 코드 `404` 실패 응답 추가됨
- `200` 응답에서 선택 속성 `createdAt` 제거됨
- `200` 응답에서 선택 속성 `online` 제거됨
- `200` 응답에서 선택 속성 `updatedAt` 제거됨

## 컴포넌트(스키마)

- 스키마 `BinaryContentResponse` 제거됨
- 스키마 `ChannelResponse` 제거됨
- 스키마 `ChannelUpdateRequest` 제거됨
- 스키마 `MessageResponse` 제거됨
- 스키마 `ReadStatusResponse` 제거됨
- 스키마 `UserResponse` 제거됨
- 스키마 `UserStatusResponse` 제거됨

## 정보

- API 버전이 `v0`에서 ``(빈 값)으로 변경됨