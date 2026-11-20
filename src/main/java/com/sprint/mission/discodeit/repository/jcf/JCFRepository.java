package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Common;
import com.sprint.mission.discodeit.repository.CrudRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class JCFRepository<T extends Common> implements CrudRepository<T> {

  private final Map<UUID, T> data;

  protected JCFRepository() {
    data = new ConcurrentHashMap<>();
  }

  @Override
  public T save(T entity) {
    data.put(entity.getId(), entity);
    return data.get(entity.getId());
  }

  @Override
  public Optional<T> findById(UUID id) {
    return Optional.ofNullable(data.get(id));
  }

  @Override
  public List<T> findAll() {
    return new ArrayList<>(data.values());
  }

  @Override
  public boolean existsById(UUID id) {
    return data.containsKey(id);
  }

  @Override
  public void deleteById(UUID id) {
    data.remove(id);
  }
}
