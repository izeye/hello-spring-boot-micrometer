package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.core.instrument.Tag;
import org.springframework.boot.actuate.metrics.web.client.RestTemplateExchangeTags;
import org.springframework.boot.actuate.metrics.web.client.RestTemplateExchangeTagsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * {@link Configuration} for Micrometer.
 *
 * @author Johnny Lim
 */
@Configuration
public class MicrometerConfig {

    @Bean
    public CustomRestTemplateExchangeTagsProvider restTemplateExchangeTagsProvider() {
        return new CustomRestTemplateExchangeTagsProvider();
    }

    static class CustomRestTemplateExchangeTagsProvider implements RestTemplateExchangeTagsProvider {

        @Override
        public Iterable<Tag> getTags(String urlTemplate, HttpRequest request, ClientHttpResponse response) {
            String url = StringUtils.hasText(urlTemplate) ? urlTemplate : RestTemplateExchangeTags.uri(request).getValue();
            return Arrays.asList(RestTemplateExchangeTags.method(request), RestTemplateExchangeTags.uri(removeQueryPart(url)),
                    RestTemplateExchangeTags.status(response), RestTemplateExchangeTags.clientName(request),
                    RestTemplateExchangeTags.outcome(response));
        }

        private String removeQueryPart(String uri) {
            int queryStartIndex = uri.indexOf('?');
            return (queryStartIndex != -1) ? uri.substring(0, queryStartIndex) : uri;
        }

    }

}
