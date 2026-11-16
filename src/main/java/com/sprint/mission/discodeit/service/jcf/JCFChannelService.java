package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class JCFChannelService implements ChannelService {

  private final Map<UUID, Channel> data;

  public JCFChannelService() {
    data = new HashMap<>();
  }

  @Override
  public Channel create(String name, String description, ChannelType type) {
    Channel channel = new Channel(name, description, type);
    data.put(channel.getId(), channel);
    return data.get(channel.getId());
  }

  @Override
  public Channel find(UUID id) {
    return findByIdOrThrow(id);
  }

  @Override
  public List<Channel> findAll() {
    return new ArrayList<>(data.values());
  }

  @Override
  public Channel update(UUID id, String name, String description) {
    Channel channel = findByIdOrThrow(id);
    channel.update(name, description);
    return channel;
  }

  @Override
  public void delete(UUID id) {
    findByIdOrThrow(id);
    data.remove(id);
  }

  private Channel findByIdOrThrow(UUID id) {
    Channel channel = data.get(id);
    if (channel == null) {
      throw new NoSuchElementException("존재하지 않는 채널 id: " + id);
    }
    return channel;
  }
}
