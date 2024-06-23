package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Micrometer.
 *
 * @author Johnny Lim
 */
@Configuration
public class MicrometerConfig {

    @Bean
    public Counter counter1(MeterRegistry meterRegistry) {
        return Counter.builder("my.counter").tag("key1", "value1").register(meterRegistry);
    }

    @Bean
    public Counter counter2(MeterRegistry meterRegistry) {
        return Counter.builder("my.counter").tag("key1", "value1").tag("key2", "value2").register(meterRegistry);
    }

}
