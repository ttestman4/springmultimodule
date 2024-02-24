package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	// Manual Test
	// curl localhost:8888/foo/dev | jq

// 	The HTTP service has resources in the following form:

// /{application}/{profile}[/{label}]
// /{application}-{profile}.yml
// /{label}/{application}-{profile}.yml
// /{application}-{profile}.properties
// /{label}/{application}-{profile}.properties
// For example:

// curl localhost:8888/foo/development
// curl localhost:8888/foo/development/master
// curl localhost:8888/foo/development,db/master
// curl localhost:8888/foo-development.yml
// curl localhost:8888/foo-db.properties
// curl localhost:8888/master/foo-db.properties
}