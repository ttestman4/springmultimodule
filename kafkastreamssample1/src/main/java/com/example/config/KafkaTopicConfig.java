package com.example.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.streams.RecoveringDeserializationExceptionHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper.TypePrecedence;
import org.springframework.util.backoff.FixedBackOff;

import com.common.Employee;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class KafkaTopicConfig {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Bean
    public NewTopic empName() {
        return new NewTopic("EmpNameIn", 1, (short) 1);
    }

    @Bean
    public NewTopic empNameDLT() {
        return new NewTopic("EmpNameIn.DLT", 1, (short) 1);
    }

    @Bean
    public NewTopic empAge() {
        return new NewTopic("EmpAgeIn", 1, (short) 1);
    }

    @Bean
    public NewTopic empAgeDLT() {
        return new NewTopic("EmpAgeIn.DLT", 1, (short) 1);
    }

    @Bean
    public NewTopic empAddress() {
        return new NewTopic("EmpAddressIn", 1, (short) 1);
    }

    @Bean
    public NewTopic empAddressDLT() {
        return new NewTopic("EmpAddressIn.DLT", 1, (short) 1);
    }

    // @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    // public KafkaStreamsConfiguration kStreamsConfigs() {
    //     Map<String, Object> props = new HashMap<>();
    //     props.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG,
    //             RecoveringDeserializationExceptionHandler.class);
    //     props.put(RecoveringDeserializationExceptionHandler.KSTREAM_DESERIALIZATION_RECOVERER, recoverer());
    //     return new KafkaStreamsConfiguration(props);
    // }

    // @Bean
    // public DeadLetterPublishingRecoverer recoverer() {
    //     return new DeadLetterPublishingRecoverer(kafkaTemplate);
    // }

}
