package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@SpringBootApplication
public class Application {
    private static Logger privateLOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        // SpringApplication.run(Application.class, args).close();
    }

    // @Bean
    // @Profile("default") // Do not run from tests
    // public ApplicationRunner runner() {
    // return args -> {
    // privateLOGGER.info("Hit Enter to terminate...");
    // System.in.read();
    // };
    // }

    // @Bean
    // public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
    //     return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
    //             .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
    //             .build());
    // }

    // @Bean
    // public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizerReactive() {
    //     return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
    //             .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()).build());
    // }
}
