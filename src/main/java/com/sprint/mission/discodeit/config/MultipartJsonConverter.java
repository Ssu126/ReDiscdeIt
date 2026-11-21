package com.sprint.mission.discodeit.config;

import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractJacksonHttpMessageConverter;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Component
public class MultipartJsonConverter extends AbstractJacksonHttpMessageConverter<JsonMapper> {

  public MultipartJsonConverter(JsonMapper jsonMapper) {
    // Json 변환기를 쓰면서, octet-stream을 받는다.
    super(jsonMapper, MediaType.APPLICATION_OCTET_STREAM);
  }

  // octet-stream을 응답이 아닌, 읽기 전용으로 설정
  @Override
  public boolean canWrite(ResolvableType type, Class<?> clazz, MediaType mediaType) {
    return false;
  }
}
