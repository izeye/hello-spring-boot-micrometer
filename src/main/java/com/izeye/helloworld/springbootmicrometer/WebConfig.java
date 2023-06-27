package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jetty.JettyClientMetrics;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.http.HttpClientTransportOverHTTP;
import org.eclipse.jetty.io.ClientConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for web.
 *
 * @author Johnny Lim
 */
@Configuration
public class WebConfig {

    @Bean
    public HttpClient getHttpClient(final MeterRegistry registry) throws Exception {
        final var clientConnector = new ClientConnector();
        final var jettyClientMetrics = JettyClientMetrics.builder(registry, (request, result) -> result.getRequest().getURI().getPath()).build();
        HttpClient httpClient = new HttpClient(new HttpClientTransportOverHTTP(clientConnector));
        httpClient.getRequestListeners().add(jettyClientMetrics);

        // This seems to be required.
        httpClient.start();

        return httpClient;
    }

}
