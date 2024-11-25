package com.izeye.helloworld.springbootmicrometer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for {@link HelloSpringBootMicrometerApplication}.
 *
 * @author Johnny Lim
 */
@SpringBootTest(properties = "spring.cache.type=simple")
class HelloSpringBootMicrometerApplicationTests {

	@Test
	void contextLoads() {
	}

}
