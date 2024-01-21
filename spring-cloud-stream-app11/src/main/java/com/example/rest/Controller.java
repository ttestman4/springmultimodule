package com.example.rest;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.Employee;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Controller {
    private static Logger privateLOGGER = LoggerFactory.getLogger(Controller.class);

    private final StreamBridge streamBridge;

    private AtomicInteger seed1 = new AtomicInteger(0);

    @PostMapping("/generate/employee/{what}")
    public void sendEmployee(@PathVariable int what) {
        for (int i = 0; i < what; i++) {
            Employee emp = Employee.builder().EmpId("id" + seed1.incrementAndGet()).build();
            streamBridge.send("rest-out-0", MessageBuilder.withPayload(emp)
                    .setHeader(KafkaHeaders.KEY, emp.EmpId())
                    .build());
        }
        privateLOGGER.debug("Employee {} generated", what);
    }

    @PostMapping("/generateOne/employee/{what}")
    public void sendOneEmployee(@PathVariable int what) {
        Employee emp = Employee.builder().EmpId("id" + what).build();
        streamBridge.send("rest-out-0", MessageBuilder.withPayload(emp)
                    .setHeader(KafkaHeaders.KEY, emp.EmpId())
                    .build());
    }
}
