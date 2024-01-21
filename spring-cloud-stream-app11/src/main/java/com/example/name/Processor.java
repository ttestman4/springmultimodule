package com.example.name;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.example.common.Employee;

@Configuration
public class Processor {
	@Bean
	public Function<Message<Employee>, Message<Employee>> processName() {
		return (msg) -> {
			Employee emp = BusinessObject.nameBusinessLogic(msg.getPayload());
			return MessageBuilder.withPayload(emp)
					// Propagate the partion key
					.setHeader(KafkaHeaders.KEY, msg.getHeaders().get(KafkaHeaders.RECEIVED_KEY))
					.build();
		};
	}
}
