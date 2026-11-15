package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import java.util.UUID;

public class JavaApplication {

  static void userTest(UserService userService) {
    System.out.println("===== User 테스트 =====");

    // 등록
    User su1 = userService.create("su1", "su1@email.com", "su1111");
    User su2 = userService.create("su2", "su2@email.com", "su2222");
    System.out.println("등록: " + su1.getUsername() + ", " + su2.getUsername());

    // 조회(단건, 다건)
    System.out.println("단건 조회: " + userService.find(su1.getId()).getUsername());
    System.out.println("다건 조회: " + userService.findAll().size() + "명");
    userService.findAll().forEach(u -> System.out.println("  - " + u.getUsername()));

    // 수정 + 수정된 데이터 조회
    userService.update(su1.getId(), "su11", null, null);
    User updated = userService.find(su1.getId());
    System.out.println(
        "수정 후: " + updated.getUsername() + " (updatedAt=" + updated.getUpdatedAt() + ")");

    // 삭제 + 삭제 확인
    userService.delete(su2.getId());
    System.out.println("삭제 후 전체: " + userService.findAll().size() + "명");
    try {
      userService.find(su2.getId());
      System.out.println("실패: 삭제된 유저가 조회됨");
    } catch (Exception e) {
      System.out.println("삭제 확인: " + e.getMessage());
    }
  }

  static void channelTest(ChannelService channelService) {
    System.out.println("\n===== Channel 테스트 =====");

    // 등록
    Channel notice = channelService.create("공지", "공지 채널입니다.");
    Channel free = channelService.create("자유", "자유롭게 대화하는 채널입니다.");
    System.out.println("등록: " + notice.getName() + ", " + free.getName());

    // 조회(단건, 다건)
    Channel found = channelService.find(notice.getId());
    System.out.println("단건 조회: " + found.getName() + " - " + found.getDescription());
    System.out.println("다건 조회: " + channelService.findAll().size() + "개");
    channelService.findAll().forEach(c -> System.out.println("  - " + c.getName()));

    // 수정 (설명만 변경, 이름은 null → 기존 값 유지) + 수정된 데이터 조회
    channelService.update(notice.getId(), null, "중요 공지 채널입니다.");
    Channel updated = channelService.find(notice.getId());
    System.out.println(
        "수정 후: "
            + updated.getName()
            + " - "
            + updated.getDescription()
            + " (updatedAt="
            + updated.getUpdatedAt()
            + ")");

    // 삭제 + 삭제 확인
    channelService.delete(free.getId());
    System.out.println("삭제 후 전체: " + channelService.findAll().size() + "개");
    try {
      channelService.find(free.getId());
      System.out.println("실패: 삭제된 채널이 조회됨");
    } catch (Exception e) {
      System.out.println("삭제 확인: " + e.getMessage());
    }
  }

  static void messageTest(
      UserService userService, ChannelService channelService, MessageService messageService) {
    System.out.println("\n===== Message 테스트 =====");

    User author = userService.create("su3", "su3@email.com", "su3333");
    Channel channel = channelService.create("잡담", "잡담 채널입니다.");

    // 등록
    Message hello = messageService.create("안녕.", author.getId(), channel.getId());
    Message bye = messageService.create("잘 가.", author.getId(), channel.getId());
    System.out.println("등록: " + hello.getContent() + ", " + bye.getContent());

    // 조회(단건, 다건)
    System.out.println("단건 조회: " + messageService.find(hello.getId()).getContent());
    System.out.println("다건 조회: " + messageService.findAll().size() + "개");
    messageService.findAll().forEach(m -> System.out.println("  - " + m.getContent()));

    // 수정 + 수정된 데이터 조회
    messageService.update(hello.getId(), "안녕하세요.");
    Message updated = messageService.find(hello.getId());
    System.out.println(
        "수정 후: " + updated.getContent() + " (updatedAt=" + updated.getUpdatedAt() + ")");

    // 삭제 + 삭제 확인
    messageService.delete(bye.getId());
    System.out.println("삭제 후 전체: " + messageService.findAll().size() + "개");
    try {
      messageService.find(bye.getId());
      System.out.println("실패: 삭제된 메시지가 조회됨");
    } catch (Exception e) {
      System.out.println("삭제 확인: " + e.getMessage());
    }
  }

  // 심화: 도메인 간 관계 검증
  static void validationTest(
      UserService userService, ChannelService channelService, MessageService messageService) {
    System.out.println("\n===== 검증 테스트 =====");

    User user = userService.create("su4", "su4@email.com", "su4444");
    Channel channel = channelService.create("검증", "검증용 채널입니다.");

    // 존재하지 않는 유저
    try {
      messageService.create("유령 메시지", UUID.randomUUID(), channel.getId());
      System.out.println("실패: 예외가 발생하지 않음");
    } catch (Exception e) {
      System.out.println("존재하지 않는 유저 → " + e.getMessage());
    }

    // 존재하지 않는 채널
    try {
      messageService.create("유령 채널 메시지", user.getId(), UUID.randomUUID());
      System.out.println("실패: 예외가 발생하지 않음");
    } catch (Exception e) {
      System.out.println("존재하지 않는 채널 → " + e.getMessage());
    }

    // 빈 메시지
    try {
      messageService.create("   ", user.getId(), channel.getId());
      System.out.println("실패: 예외가 발생하지 않음");
    } catch (Exception e) {
      System.out.println("빈 메시지 → " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    // 서비스 초기화 (Message는 User, Channel 서비스에 의존)
    UserService userService = new JCFUserService();
    ChannelService channelService = new JCFChannelService();
    MessageService messageService = new JCFMessageService(userService, channelService);

    userTest(userService);
    channelTest(channelService);
    messageTest(userService, channelService, messageService);
    validationTest(userService, channelService, messageService);
  }
}