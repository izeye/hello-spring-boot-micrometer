package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for cache metrics.
 *
 * @author Johnny Lim
 */
@SpringBootTest
@DirtiesContext
class CacheMetricsTests {

    @Autowired
    PersonService personService;

    @Autowired
    MeterRegistry meterRegistry;

    @Disabled("See https://github.com/micrometer-metrics/micrometer/blob/9537358666e75c482815ca4cab632a44ce8c7a16/micrometer-core/src/main/java/io/micrometer/core/instrument/binder/cache/JCacheMetrics.java#L104-L108")
    @Test
    void cacheSize() {
        assertThat(this.meterRegistry.get("cache.size").tag("cache", "persons").gauge().value()).isEqualTo(0);

        String username = "izeye";
        Person person = this.personService.getPerson(username);
        assertThat(this.personService.getPerson(username)).isSameAs(person);

        assertThat(this.meterRegistry.get("cache.size").tag("cache", "persons").gauge().value()).isEqualTo(1);
    }

}
