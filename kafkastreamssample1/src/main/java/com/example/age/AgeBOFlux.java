package com.example.age;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.common.Employee;

@Component
public class AgeBOFlux {
    private final WebClient client;

    public AgeBOFlux(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:8091").build();
    }

    public Employee ageBusinessLogic(Employee empIn) {
        return this.client.post()
                .uri("/employees/age")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(empIn)
                .retrieve()
                .bodyToMono(Employee.class)
                // .delayElement(Duration.ofSeconds(100))
                .block();
    }

}
