package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Micrometer {@link PrometheusMeterRegistry}.
 *
 * @author Johnny Lim
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureObservability
@AutoConfigureWebTestClient
class PrometheusMeterRegistryTests {

    @Autowired
    PrometheusMeterRegistry registry;

    @Autowired
    WebTestClient client;

    @Test
    void httpServerRequestsTimerWhenPercentilesAndSlosAreSetShouldHaveOnlySlos() throws InterruptedException {
        this.client.get().uri("/actuator").exchange().expectStatus().isOk();

        // IntelliJ is okay without this delay, but Gradle on console requires this delay for some reason.
        TimeUnit.SECONDS.sleep(1);

        String output = this.registry.scrape();
        String[] lines = output.split("\n");
        assertThat(lines)
                .noneSatisfy((line) -> assertThat(line)
                        .startsWith("http_server_requests_seconds{"))
                .anySatisfy((line) -> assertThat(line)
                        .startsWith("http_server_requests_seconds_bucket{")
                        .contains("le=\"0.001\""));
    }

}
