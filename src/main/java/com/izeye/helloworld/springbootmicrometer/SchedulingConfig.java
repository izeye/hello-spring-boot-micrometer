package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * {@link Configuration} for scheduling.
 *
 * @author Johnny Lim
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    private static final Logger log = LoggerFactory.getLogger(SchedulingConfig.class);

    private final Tracer tracer;

    public SchedulingConfig(Tracer tracer) {
        this.tracer = tracer;
    }

    @Scheduled(initialDelay = 1_000, fixedDelay = 1_000)
    public void greet() {
        TraceContext context = tracer.currentSpan().context();
        log.info("Trace ID: {}, parent span ID: {}, span ID: {}", context.traceId(), context.parentId(), context.spanId());
    }

}
