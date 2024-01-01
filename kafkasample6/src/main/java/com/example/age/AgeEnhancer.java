package com.example.age;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
// import org.springframework.boot.configurationprocessor.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.common.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgeEnhancer {

    private final KafkaTemplate<Object, String> kafkaTemplate;
    private final ObjectMapper jacksonObjectMapper;
    
    private static Logger privateLOGGER = LoggerFactory.getLogger(AgeEnhancer.class);
    private AtomicInteger seed = new AtomicInteger(10);

    @KafkaListener(id = "ageGroup", topics = "EmpAgeIn")
    public void listen(ConsumerRecord<Object, String> payloadIn) {
        privateLOGGER.info("Received ageGroup: jsonInString: {}: raw: {}", payloadIn.value(), payloadIn);
        Employee emp;
        try {
            emp = jacksonObjectMapper.readValue(payloadIn.value(), Employee.class);
        } catch (Exception e) {
            privateLOGGER.info("JSON Deserialization Exception: ", e);
            emp = Employee.builder().EmpId(null).build();
        }
        Employee enhanedEmployee = emp.withEmpAge((short)(seed.incrementAndGet() % 80));
        String temp1;
        try {
            temp1 = jacksonObjectMapper.writeValueAsString(enhanedEmployee);
        } catch (Exception e) {
            privateLOGGER.info("JSON serialization Exception: ", e);
            temp1 = "{}";
        }
        
        JSONObject payloadOut = Utility.mergeJSONObjects(new JSONObject(payloadIn.value()), new JSONObject(temp1));

        kafkaTemplate.send("EmpAddressIn", payloadOut.toString());
    }

}
