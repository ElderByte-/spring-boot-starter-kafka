package com.elderbyte.kafka.consumer.factory;

import com.elderbyte.kafka.producer.KafkaMessage;
import com.elderbyte.messaging.annotations.MessageKey;
import com.elderbyte.messaging.annotations.MessageMetadata;
import com.elderbyte.messaging.annotations.Tombstone;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MessageAnnotationProcessorTest {

    public static class SampleMessage {
        @MessageKey(populate = false)
        public String id = "uno";

        @MessageMetadata
        public String meta;
    }

    @Tombstone
    public static class SampleMessageDeleted {
        @MessageKey
        public String id;

        @MessageMetadata
        public String meta;
    }

    public static class SampleMessageMap {
        @MessageKey(populate = false)
        public String id = "uno";

        @MessageMetadata
        public String meta;

        @MessageMetadata
        public Map<String, String> headers;
    }


    @Test
    public void buildMessage() {

        var headers = new HashMap<String, String>();
        headers.put("meta", "due");

        var msg = new SampleMessage();
        var record = KafkaMessage.build( "key", msg, headers).toConsumerRecord("top", 1);

        var buildMessage = MessageAnnotationProcessor.buildMessage(record);

        assertEquals("uno", buildMessage.id);
        assertEquals("due", buildMessage.meta);
    }

    @Test
    public void buildMessageTombstone() {
        var headers = new HashMap<String, String>();
        headers.put("meta", "due");

        var record = KafkaMessage.tombstone( "key-value", headers)
                .toConsumerRecord("top", 1);

        var deletedMessage = MessageAnnotationProcessor.buildMessageTombstone(record, SampleMessageDeleted.class);

        assertEquals("key-value", deletedMessage.id);
        assertEquals("due", deletedMessage.meta);
    }

    @Test
    public void buildMessage_headers() {

        var headers = new HashMap<String, String>();
        headers.put("meta", "due");
        headers.put("foo", "bar");

        var msg = new SampleMessageMap();
        var record = KafkaMessage.build( "key", msg, headers).toConsumerRecord("top", 1);

        var buildMessage = MessageAnnotationProcessor.buildMessage(record);

        assertEquals("uno", buildMessage.id);
        assertEquals("due", buildMessage.meta);

        assertEquals("due", buildMessage.headers.get("meta"));
        assertEquals("bar", buildMessage.headers.get("foo"));
    }
}