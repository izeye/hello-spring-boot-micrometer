package com.izeye.helloworld.springbootmicrometer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.metrics.AutoConfigureMetrics;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Tests for Spring Boot Actuator.
 *
 * @author Johnny Lim
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.cache.type=simple")
@AutoConfigureMetrics
class SpringBootActuatorTests {

    @Autowired
    WebTestClient client;

    @Test
    void prometheus() {
        this.client.get().uri("/actuator/prometheus").exchange().expectStatus().isOk();
    }

}
