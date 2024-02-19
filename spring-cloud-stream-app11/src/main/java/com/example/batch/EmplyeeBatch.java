package com.example.batch;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.integration.support.MessageBuilder;

import com.example.common.Employee;

@Configuration
public class EmplyeeBatch {
    private static Logger privateLOGGER = LoggerFactory.getLogger(EmplyeeBatch.class);

    private final AtomicInteger seed = new AtomicInteger(0);
    private static final boolean enableTwoKeyMode = false;

    @Bean
    public Supplier<Message<Employee>> employeeFeed() {
        return () -> {
            int seedVal = seed.incrementAndGet();
            int empId = enableTwoKeyMode ? seedVal % 2 : seedVal;
            Employee emp = Employee.builder().EmpId( String.valueOf(empId)).build();
            privateLOGGER.debug("Employee {} generated", emp);
            return MessageBuilder.withPayload(emp)
                    .setHeader(KafkaHeaders.KEY, emp.EmpId())
                    .build();
        };
    }
}
