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
// curl -X POST http://localhost:8091/generate/employee/1
// curl -X POST http://localhost:8091/generate/fish/1
//  curl -X POST localhost:8091/employees/age -H 'Content-type:application/json' -d '{"EmpId": "id1", "FirstName": "FN1"}'