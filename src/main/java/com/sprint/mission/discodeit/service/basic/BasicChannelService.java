package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;

  public BasicChannelService(ChannelRepository channelRepository) {
    this.channelRepository = channelRepository;
  }

  @Override
  public Channel create(String name, String description, ChannelType type) {
    Channel channel = new Channel(name, description, type);
    return channelRepository.save(channel);
  }

  @Override
  public Channel find(UUID id) {
    return findChannelOrThrow(id);
  }

  @Override
  public List<Channel> findAll() {
    return channelRepository.findAll();
  }

  @Override
  public Channel update(UUID id, String name, String description) {
    Channel channel = findChannelOrThrow(id);
    channel.update(name, description);
    return channelRepository.save(channel);
  }

  @Override
  public void delete(UUID id) {
    findChannelOrThrow(id);
    channelRepository.deleteById(id);
  }

  private Channel findChannelOrThrow(UUID id) {
    return channelRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 채널 id: " + id));
  }
}
