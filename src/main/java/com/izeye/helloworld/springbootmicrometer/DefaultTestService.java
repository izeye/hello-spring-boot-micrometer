package com.izeye.helloworld.springbootmicrometer;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * Default {@link TestService}.
 *
 * @author Johnny Lim
 */
@Service
public class DefaultTestService implements TestService {

    private final RestTemplate restTemplate;

    public DefaultTestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Override
    public Map<String, Object> testRestTemplate() {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://httpbin.org/headers")
                .queryParam("a", "test")
                .queryParam("b", "테스트")
                .build()
                .encode()
                .toUri();
        return this.restTemplate.getForObject(uri, Map.class);
    }

}
