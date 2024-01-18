package app1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringKafkaApp1Tests {

	@Test
	void contextLoads() {
	}

}

// curl -X POST  http://localhost:8081/publish/demo1
// curl -X POST  http://localhost:8081/publish/demo2