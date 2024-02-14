package com.example.name;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.common.Employee;

@Configuration
public class Processor {
    private final WebClient client;

	public Processor(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:8096").build();
    }

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

	@Bean
	public Function<Message<Employee>, Message<Employee>> processNameViaRest() {
		return (msg) -> {
			Employee empIn = msg.getPayload();
			Employee empOut = this.client.post()
                .uri("/employees/name")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(empIn)
                .retrieve()
                .bodyToMono(Employee.class)
                // .delayElement(Duration.ofSeconds(100))
                .block();
			
			return MessageBuilder.withPayload(empOut)
				// Propagate the partion key
				.setHeader(KafkaHeaders.KEY, msg.getHeaders().get(KafkaHeaders.RECEIVED_KEY))
				.build();
		};
	}
}
