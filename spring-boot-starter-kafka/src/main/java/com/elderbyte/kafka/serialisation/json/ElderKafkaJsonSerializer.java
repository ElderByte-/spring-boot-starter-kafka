package com.elderbyte.kafka.serialisation.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Serialize objects to UTF-8 JSON. This works with any object which is serializable with Jackson.
 */
public class ElderKafkaJsonSerializer<V> implements Serializer<V> {

  /***************************************************************************
   *                                                                         *
   * Fields                                                                  *
   *                                                                         *
   **************************************************************************/

  private final ObjectMapper objectMapper;

  /***************************************************************************
   *                                                                         *
   * Constructors                                                            *
   *                                                                         *
   **************************************************************************/

  public ElderKafkaJsonSerializer() {
    this(DefaultJsonMapper.buildDefaultMapper());
  }

  public ElderKafkaJsonSerializer(ObjectMapper mapper) {
    this.objectMapper = mapper;
  }

  /***************************************************************************
   *                                                                         *
   * Public API                                                              *
   *                                                                         *
   **************************************************************************/

  @Override
  public void configure(Map<String, ?> config, boolean isKey) {
    // NOP
  }


  @Override
  public byte[] serialize(String topic, V data) {
    if (data == null) {
      return null;
    }

    try {
      return objectMapper.writeValueAsBytes(data);
    } catch (Exception e) {
      throw new SerializationException("Error serializing JSON message", e);
    }
  }

  @Override
  public void close() {
  }

}
