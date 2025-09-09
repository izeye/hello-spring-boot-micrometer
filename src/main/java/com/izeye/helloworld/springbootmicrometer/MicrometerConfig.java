package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.common.KeyValue;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.observation.ClientHttpObservationDocumentation;
import org.springframework.http.client.observation.ClientRequestObservationContext;
import org.springframework.http.client.observation.DefaultClientRequestObservationConvention;

import java.util.regex.Pattern;

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

    @Bean
    public CustomClientRequestObservationConvention clientRequestObservationConvention() {
        return new CustomClientRequestObservationConvention();
    }

    public static class CustomClientRequestObservationConvention extends DefaultClientRequestObservationConvention {

        private static final Pattern PATTERN_BEFORE_PATH = Pattern.compile("^https?://[^/]+");

        @Override
        protected KeyValue uri(ClientRequestObservationContext context) {
            // Remove query part for better safety as even if "url" is provided as a String, it might not be a template.
            return KeyValue.of(ClientHttpObservationDocumentation.LowCardinalityKeyNames.URI, removeQueryPart(getUri(context)));
        }

        private String getUri(ClientRequestObservationContext context) {
            if (context.getUriTemplate() != null) {
                return extractPath(context.getUriTemplate());
            }
            return context.getCarrier().getURI().getPath();
        }

        private static String extractPath(String uriTemplate) {
            String path = PATTERN_BEFORE_PATH.matcher(uriTemplate).replaceFirst("");
            return (path.startsWith("/") ? path : "/" + path);
        }

        private String removeQueryPart(String uri) {
            int queryStartIndex = uri.indexOf('?');
            return (queryStartIndex != -1) ? uri.substring(0, queryStartIndex) : uri;
        }

    }

}
