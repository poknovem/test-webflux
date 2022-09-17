package com.springreactive.testwebflux;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.4.8")
class TestWebfluxApplicationTests {

	@Test
	void contextLoads() {
	}

}
