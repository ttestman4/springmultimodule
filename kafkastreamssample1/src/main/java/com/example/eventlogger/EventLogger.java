package com.example.eventlogger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.KafkaHeaders;

import com.common.Employee;

@Component
@KafkaListener(id = "multiGroup", topics = { "EmpNameIn", "EmpNameIn.DLT", "EmpAgeIn", "EmpAgeIn.DLT", "EmpAddressIn",
        "EmpAddressIn.DLT" })
public class EventLogger {

    private static Logger privateLOGGER = LoggerFactory.getLogger(EventLogger.class);

    @KafkaHandler
    public void employeeDeser(Employee payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.OFFSET) long offset) {
        privateLOGGER.info("Received Employee: {}: from {} @ {}:", payload, topic, offset);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(ConsumerRecord<Object, Object> object) {
        privateLOGGER.info("Received unknown: jsonString: {} from {} @ {}: raw: {}", object.value(), object.topic(),
                object.offset(), object);
    }
}
