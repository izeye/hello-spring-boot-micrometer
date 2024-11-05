package learningtest.io.micrometer.observation;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Tests for {@link Observation}.
 *
 * @author Johnny Lim
 */
class ObservationTests {

    @Test
    void wrap() {
        MeterRegistry meterRegistry = new SimpleMeterRegistry();

        ObservationRegistry observationRegistry = ObservationRegistry.create();
        observationRegistry.observationConfig().observationHandler(new DefaultMeterObservationHandler(meterRegistry));

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.afterPropertiesSet();

        String observationName = "tasks.execution";
        Observation observation = Observation.createNotStarted(observationName, observationRegistry);
        assertThat(meterRegistry.find(observationName).timer()).isNull();

        CountDownLatch latch = new CountDownLatch(1);
        taskExecutor.submit(observation.wrap(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        assertThat(meterRegistry.find(observationName).timer()).isNull();

        latch.countDown();
        await().untilAsserted(() -> assertThat(meterRegistry.find(observationName).timer().count()).isEqualTo(1));
    }

}
