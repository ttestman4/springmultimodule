package com.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class Sample05Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Sample05Application.class, args);
    }

    @KafkaListener(id ="sampleListener", topics = "topic1")
    void listenForTopic(String payload) {
        System.out.println("Received: " + payload);
    }

    @Bean
    public NewTopic foos() {
        return new NewTopic("topic1", 1, (short)1);
    }
}
