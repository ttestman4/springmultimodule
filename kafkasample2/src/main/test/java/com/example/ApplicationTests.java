package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
public class ApplicationTests {
    
    @Test
    public void contextLoads() {

    }
}

// Maual testing
// curl -X POST http://localhost:8084/send/foo/msg1
// curl -X POST http://localhost:8084/send/bar/msg2
// curl -X POST http://localhost:8084/send/unknown/xxx