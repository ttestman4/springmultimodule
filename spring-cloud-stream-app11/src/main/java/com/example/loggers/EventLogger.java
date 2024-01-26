package com.example.loggers;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import com.example.common.Employee;

@Configuration
public class EventLogger {
    private static Logger privateLOGGER = LoggerFactory.getLogger(EventLogger.class);

    @Bean    
    @ConditionalOnProperty("config.enable-employee-listner")
    Consumer<Message<Employee>> eventListener() {
        return msg -> {
            privateLOGGER.info("Topic: {}:  Partition: {}:  Received Key: {}: Payload: {}:  ",
                    msg.getHeaders().get(KafkaHeaders.RECEIVED_TOPIC),
                    msg.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION),
                    msg.getHeaders().get(KafkaHeaders.RECEIVED_KEY),
                    msg.getPayload());
        };
    }

    @ConditionalOnProperty("config.enable-string-listner")
    @Bean
    public Consumer<Message<String>> print() {
        return msg -> privateLOGGER.info("Payload: {} : Partition: {}: Received Key: {}: Key: {}: Raw: {}:",
                msg.getPayload(),
                msg.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION),
                msg.getHeaders().get(KafkaHeaders.RECEIVED_KEY),
                msg.getHeaders().get(KafkaHeaders.KEY),
                msg);
    }
}
