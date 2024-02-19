package com.example.name;

import java.util.function.Function;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.common.Employee;

import reactor.core.publisher.Mono;

@Configuration
public class Processor {
	private final RestTemplate rest;
	private final WebClient client;
	private final ReactiveCircuitBreakerFactory cbFactoryReactive;
	private final CircuitBreakerFactory cbFactory;

	private static boolean enableReactive = true;
	private static boolean enableCircuitBreaker = false;

	public Processor(RestTemplateBuilder restTemplateBuilder, WebClient.Builder builder,
			CircuitBreakerFactory cbFactory,
			ReactiveCircuitBreakerFactory cbFactoryReactive) {
		this.rest = restTemplateBuilder.rootUri("http://localhost:8096").build();
		this.client = builder.baseUrl("http://localhost:8096").build();
		this.cbFactory = cbFactory;
		this.cbFactoryReactive = cbFactoryReactive;
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
	public Function<Message<Employee>, Message<Employee>> processNameViaRestRetry() {
		return this.processNameViaRest();
	}

	@Bean
	public Function<Message<Employee>, Message<Employee>> processNameViaRest() {
		return (msg) -> {
			Employee empIn = msg.getPayload();
			Employee empOut;
			if (enableReactive) {
				if (enableCircuitBreaker) {
					empOut = this.client.post()
							.uri("/employees/name")
							.accept(MediaType.APPLICATION_JSON)
							.bodyValue(empIn)
							.retrieve()
							.bodyToMono(Employee.class)
							// .delayElement(Duration.ofSeconds(100))
							.transform(
									it -> cbFactoryReactive.create("nameRest").run(it))
							.block();
				} else {
					empOut = this.client.post()
							.uri("/employees/name")
							.accept(MediaType.APPLICATION_JSON)
							.bodyValue(empIn)
							.retrieve()
							.bodyToMono(Employee.class)
							.block();
				}
			} else {
				if (enableCircuitBreaker) {
					empOut = cbFactory.create("nameRest")
							.run(() -> this.rest.postForObject("/employees/name", empIn, Employee.class));
				} else {
					empOut = this.rest.postForObject("/employees/name", empIn, Employee.class);
				}
			}

			return MessageBuilder.withPayload(empOut)
					// Propagate the partion key
					.setHeader(KafkaHeaders.KEY, msg.getHeaders().get(KafkaHeaders.RECEIVED_KEY))
					.build();
		};
	}
}
