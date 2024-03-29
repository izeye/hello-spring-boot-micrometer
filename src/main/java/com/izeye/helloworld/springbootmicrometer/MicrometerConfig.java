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
            KeyValue keyValue = super.uri(context);
            if (keyValue.getValue().equals(KeyValue.NONE_VALUE)) {
                String requestUri = context.getCarrier().getURI().toString();
                // Skip "https://"
                int pathStartIndex = requestUri.indexOf('/', 8);
                int queryStartIndex = requestUri.indexOf('?');
                requestUri = requestUri.substring(pathStartIndex, (queryStartIndex != -1) ? queryStartIndex : requestUri.length());
                return KeyValue.of(ClientHttpObservationDocumentation.LowCardinalityKeyNames.URI, requestUri);
            }
            return keyValue;
        }

    }

}
