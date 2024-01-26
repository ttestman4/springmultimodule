package com.example.rest;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.Employee;
import com.example.common.Fish;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Controller {
    private static Logger privateLOGGER = LoggerFactory.getLogger(Controller.class);

    private final StreamBridge streamBridge;

    private AtomicInteger seed1 = new AtomicInteger(0);
    private AtomicInteger seed2 = new AtomicInteger(0);

    @Value("${config.binder-name}")
    private String binderName;

    @PostMapping("/generate/employee/{what}")
    public void sendEmployee(@PathVariable int what) {
        for (int i = 0; i < what; i++) {
            Employee emp = Employee.builder().EmpId("id" + seed1.incrementAndGet()).build();
            streamBridge.send(binderName, MessageBuilder.withPayload(emp)
                    .setHeader(KafkaHeaders.KEY, emp.EmpId())
                    .build());
        }
        privateLOGGER.debug("Employee {} generated", what);
    }

    @PostMapping("/generateOne/employee/{what}")
    public void sendOneEmployee(@PathVariable int what) {
        privateLOGGER.debug("Binder Name: {}", binderName);
        Employee emp = Employee.builder().EmpId("id" + what).build();
        streamBridge.send(binderName, MessageBuilder.withPayload(emp)
                    .setHeader(KafkaHeaders.KEY, emp.EmpId())
                    .build());
    }

    @PostMapping("/generate/fish/{what}")
    public void sendFish(@PathVariable int what) {
        for (int i = 0; i < what; i++) {
            Fish fish = Fish.builder().EmpId(1).FishId("id" + seed2.incrementAndGet()).build();
            streamBridge.send(binderName, MessageBuilder.withPayload(fish)
                    .setHeader(KafkaHeaders.KEY, fish.FishId())
                    .build());
        }
        privateLOGGER.debug("Fish {} generated", what);
    }

    @PostMapping("/generateOne/fish/{what}")
    public void sendOneFish(@PathVariable int what) {
        privateLOGGER.debug("Binder Name: {}", binderName);
        Fish fish = Fish.builder().EmpId(1).FishId("id" + what).build();
        streamBridge.send(binderName, MessageBuilder.withPayload(fish)
                    .setHeader(KafkaHeaders.KEY, fish.FishId())
                    .build());
    }
}
