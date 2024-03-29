package com.example.producer.application;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class Application3 {
    public static void main(String[] args){
        SpringApplication.run(Application3.class, args);
    }
    
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("topic1")
        .partitions(10)
        .replicas(1)
        .build();
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send("topic1", "test4");
        };
    }

}
