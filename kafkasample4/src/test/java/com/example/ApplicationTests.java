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

// Manual Test
// curl -X POST http://localhost:8086/send/fail; curl -X POST http://localhost:8086/send/good