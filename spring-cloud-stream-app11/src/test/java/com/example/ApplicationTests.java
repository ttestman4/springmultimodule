package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	// Manual Test
	// curl -X POST http://localhost:8091/generate/employee/1
	// curl -X POST http://localhost:8091/generateOne/employee/1
	// curl -X POST http://localhost:8091/generate/fish/1
	// curl -X POST http://localhost:8091/generateOne/fish/1
}