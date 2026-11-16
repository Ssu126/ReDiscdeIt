package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;

public class JavaApplication {

  static User setupUser(UserService userService) {
    User user = userService.create("woody", "woody@codeit.com", "woody1234");
    return user;
  }

  static Channel setupChannel(ChannelService channelService) {
    Channel channel = channelService.create("공지", "공지 채널입니다.", ChannelType.PUBLIC);
    return channel;
  }

  static void messageCreateTest(MessageService messageService, Channel channel, User author) {
    Message message = messageService.create("안녕하세요.", author.getId(), channel.getId());
    System.out.println("메시지 생성: " + message.getId());
  }

  static void run(
      UserRepository userRepository,
      ChannelRepository channelRepository,
      MessageRepository messageRepository) {
    UserService userService = new BasicUserService(userRepository);
    ChannelService channelService = new BasicChannelService(channelRepository);
    MessageService messageService =
        new BasicMessageService(messageRepository, userRepository, channelRepository);

    User user = setupUser(userService);
    Channel channel = setupChannel(channelService);

    messageCreateTest(messageService, channel, user);
  }

  public static void main(String[] args) {
    System.out.println("===== JCF*Repository 테스트 =====");
    run(new JCFUserRepository(), new JCFChannelRepository(), new JCFMessageRepository());

    System.out.println("\n===== File*Repository 테스트 =====");
    run(new FileUserRepository(), new FileChannelRepository(), new FileMessageRepository());
  }
}
