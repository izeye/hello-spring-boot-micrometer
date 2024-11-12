package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.common.KeyValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.observation.ClientHttpObservationDocumentation;
import org.springframework.http.client.observation.ClientRequestObservationContext;
import org.springframework.http.client.observation.DefaultClientRequestObservationConvention;

/**
 * {@link Configuration} for Micrometer.
 *
 * @author Johnny Lim
 */
@Configuration
public class MicrometerConfig {

    @Bean
    public CustomClientRequestObservationConvention clientRequestObservationConvention() {
        return new CustomClientRequestObservationConvention();
    }

    static class CustomClientRequestObservationConvention extends DefaultClientRequestObservationConvention {

        @Override
        protected KeyValue uri(ClientRequestObservationContext context) {
            String uri = removeQueryPart(context.getCarrier().getURI().getPath());
            return KeyValue.of(ClientHttpObservationDocumentation.LowCardinalityKeyNames.URI, uri);
        }

        private String removeQueryPart(String uri) {
            int queryStartIndex = uri.indexOf('?');
            return (queryStartIndex != -1) ? uri.substring(0, queryStartIndex) : uri;
        }

    }

}
