package com.example;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.KafkaException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
public class Sample05Application2Tests {
    
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void testKafkaTemplateSend() {
        assertThatExceptionOfType(KafkaException.class)
            .isThrownBy(() ->
                this.kafkaTemplate.send("nonExistingTopic", "fake data").get(10, TimeUnit.SECONDS))
            .withRootCauseExactlyInstanceOf(TimeoutException.class)
            .withMessageContaining("Send failed")
            .withStackTraceContaining("Topic nonExistingTopic not present in metadata");
    }
}
