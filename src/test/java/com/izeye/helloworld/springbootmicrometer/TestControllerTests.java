package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TestController}.
 *
 * @author Johnny Lim
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTests {

    @Autowired
    WebTestClient client;

    @Autowired
    MeterRegistry meterRegistry;

    @Test
    void restTemplate() {
        this.client.get().uri("/test/rest-template-with-uri").exchange().expectStatus().isOk();

        long count = this.meterRegistry.get("http.client.requests").tag("uri", "/headers")
                .timer()
                .count();
        assertThat(count).isOne();
    }

}
