package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
// MeterRegistry should be cleared.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SomeServiceTests {

    @Autowired
    SomeService someService;

    @Autowired
    MeterRegistry meterRegistry;

    @Test
    void invokeRestTemplateWithUri() {
        Map<String, Object> response = this.someService.invokeRestTemplateWithUri();
        System.out.println(response);

        Timer timer = this.meterRegistry.get("http.client.requests").timer();
        assertThat(timer.getId().getTags()).contains(Tag.of("uri", "/get"));
    }

    @Test
    void invokeRestTemplateWithUriString() {
        Map<String, Object> response = this.someService.invokeRestTemplateWithUriString();
        System.out.println(response);

        Timer timer = this.meterRegistry.get("http.client.requests").timer();
        assertThat(timer.getId().getTags()).contains(Tag.of("uri", "/get"));
    }

    @Test
    void invokeRestTemplateWithUriTemplate() {
        Map<String, Object> response = this.someService.invokeRestTemplateWithUriTemplate();
        System.out.println(response);

        Timer timer = this.meterRegistry.get("http.client.requests").timer();
        assertThat(timer.getId().getTags()).contains(Tag.of("uri", "/{path}"));
    }

}
