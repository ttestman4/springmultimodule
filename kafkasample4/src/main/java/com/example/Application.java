package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.RetryTopicHeaders;
import org.springframework.kafka.retrytopic.SameIntervalTopicReuseStrategy;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.kafka.support.KafkaHeaders;

@SpringBootApplication
public class Application {

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // @RetryableTopic(attempts = "5", backoff = @Backoff(delay = 2_000, maxDelay = 10_000, multiplier = 2))
    // @KafkaListener(id = "fooGroup1", topics = "topic4")
    // public void listen(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
    //         @Header(KafkaHeaders.OFFSET) long offset) {

    //     this.logger.info("Received: {} from {} @ {}", in, topic, offset);

    //     if (in.startsWith("fail")) {
    //         throw new RuntimeException("listener-1 failed");
    //     }
    // }

    // @RetryableTopic(attempts = "5", backoff = @Backoff(delay = 2_000, maxDelay = 10_000, multiplier = 2), retryTopicSuffix = "-listener2", dltTopicSuffix = "-listener2-dlt", topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    // @KafkaListener(id = "fooGroup2", topics = "topic4")
    // public void listen2(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
    //         @Header(KafkaHeaders.OFFSET) long offset,
    //         @Header(name = KafkaHeaders.DELIVERY_ATTEMPT, required = false) Integer blockingAttempts,
    //         @Header(name = RetryTopicHeaders.DEFAULT_HEADER_ATTEMPTS, required = false) Integer nonBlockingAttempts) {

    //     this.logger.info("Received: {} from {} @ {} blockingAttempts: {} nonBlockingAttempts:{}", in, topic, offset, blockingAttempts, nonBlockingAttempts);

    //     if (in.startsWith("list2fail")) {
    //         throw new RuntimeException("listener-2 failed");
    //     }
    // }

    @RetryableTopic(attempts = "5000", backoff = @Backoff(delay = 60_000, multiplier = 1), retryTopicSuffix = "-listener3", dltTopicSuffix = "-listener3-dlt", 
    sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.SINGLE_TOPIC,
    topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    @KafkaListener(id = "fooGroup3", topics = "topic4")
    public void listen3(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.OFFSET) long offset,
            @Header(name = KafkaHeaders.DELIVERY_ATTEMPT, required = false) Integer blockingAttempts,
            @Header(name = RetryTopicHeaders.DEFAULT_HEADER_ATTEMPTS, required = false) Integer nonBlockingAttempts) {

        this.logger.info("Received: {} from {} @ {} blockingAttempts: {} nonBlockingAttempts:{}", in, topic, offset, blockingAttempts, nonBlockingAttempts);

        if (in.startsWith("list3fail")) {
            throw new RuntimeException("listener-3 failed");
        }
    }

    @DltHandler
    public void listenDlt(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.OFFSET) long offset,
            @Header(name = KafkaHeaders.DELIVERY_ATTEMPT, required = false) Integer blockingAttempts,
            @Header(name = RetryTopicHeaders.DEFAULT_HEADER_ATTEMPTS, required = false) Integer nonBlockingAttempts) {
        logger.info("Received from DLT: {} from {} @ {} blockingAttempts: {} nonBlockingAttempts:{}", in, topic, offset, blockingAttempts, nonBlockingAttempts);

    }
}
