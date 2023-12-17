package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link io.micrometer.core.instrument.binder.logging.LogbackMetrics}.
 *
 * @author Johnny Lim
 */
@SpringBootTest
class LogbackMetricsTests {

    private static final Logger log = LoggerFactory.getLogger(LogbackMetricsTests.class);

    @Autowired
    private MeterRegistry meterRegistry;

    @Test
    void info() {
        Counter counter = meterRegistry.counter("logback.events", "level", "info");
        double count = counter.count();
        log.info("test");
        assertThat(counter.count() - count).isOne();
    }

    @Disabled("See https://github.com/micrometer-metrics/micrometer/issues/4404")
    @Test
    void log() {
        Counter counter = meterRegistry.counter("logback.events", "level", "info");
        double count = counter.count();
        log.atInfo().setMessage("test").log();
        assertThat(counter.count() - count).isOne();
    }

}
