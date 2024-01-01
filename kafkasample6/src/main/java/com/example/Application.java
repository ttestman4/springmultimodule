package com.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@SpringBootApplication
public class Application {

    private static Logger privateLOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args).close();
    }

    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
    }
    // @Bean
    // public RecordMessageConverter converter() {
    // return new JsonMessageConverter();
    // }

    @Bean
    public NewTopic empName() {
        return new NewTopic("EmpNameIn", 1, (short) 1);
    }

    @Bean
    public NewTopic empAge() {
        return new NewTopic("EmpAgeIn", 1, (short) 1);
    }

    @Bean
    public NewTopic empAddress() {
        return new NewTopic("EmpAddressIn", 1, (short) 1);
    }

    @Bean
    @Profile("default") // Do not run from tests
    public ApplicationRunner runner() {
        return args -> {
            privateLOGGER.info("Hit Enter to terminate...");
            System.in.read();
        };
    }
}
