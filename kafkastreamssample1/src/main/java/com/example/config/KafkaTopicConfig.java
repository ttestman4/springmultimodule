package com.example.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper.TypePrecedence;
import org.springframework.util.backoff.FixedBackOff;

import com.common.Employee;

@Configuration
public class KafkaTopicConfig {
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
}
