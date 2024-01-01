package com.example;

import static org.awaitility.Awaitility.await;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
@DirtiesContext
public class Sample05Application1Tests {
    
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void testkafkaListener(CapturedOutput output) {
        this.kafkaTemplate.send("topic1", "testData");

        await().until(()-> output.getOut().contains("Received: testData"));
    }
}
