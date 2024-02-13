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
//  curl -X POST localhost:8096/employees/name -H 'Content-type:application/json' -d '{"EmpId": "id1", "FirstName": "FN1"}'