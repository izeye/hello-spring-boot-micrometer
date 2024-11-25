package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.distribution.HistogramSnapshot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Micrometer configuration.
 *
 * @author Johnny Lim
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.cache.type=simple")
@AutoConfigureWebTestClient
class MicrometerConfigTests {

    @Autowired
    MeterRegistry registry;

    @Autowired
    WebTestClient client;

    @Test
    void httpServerRequestsTimerShouldHavePercentiles() {
        this.client.get().uri("/actuator").exchange().expectStatus().isOk();

        Timer timer = this.registry.get("http.server.requests").timer();
        HistogramSnapshot snapshot = timer.takeSnapshot();
        assertThat(snapshot.percentileValues()).isNotEmpty();
    }

}
