package com.example.eventlogger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "multiGroup", topics = { "EmpNameIn", "EmpAgeIn", "EmpAddressIn" })
public class EventLogger {

    private static Logger privateLOGGER = LoggerFactory.getLogger(EventLogger.class);

    @KafkaHandler(isDefault = true)
    public void unknown(ConsumerRecord<Object, String> object) {
        privateLOGGER.info("Received unknown: jsonString: {} from {} @ {}: raw: {}", object.value(), object.topic(),
                object.offset(), object);
    }

}
