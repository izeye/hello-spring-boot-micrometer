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
    public Map<String, Object> testRestTemplateWithUri() {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://httpbin.org/headers")
                .queryParam("a", "test")
                .queryParam("b", "테스트")
                .build()
                .encode()
                .toUri();
        return this.restTemplate.getForObject(uri, Map.class);
    }

    @Override
    public Map<String, Object> testRestTemplateWithUrlTemplate() {
        return this.restTemplate.getForObject("https://httpbin.org/headers?a={a}", Map.class, "test");
    }

    @Override
    public Map<String, Object> testRestTemplateWithUrl() {
        return this.restTemplate.getForObject("https://httpbin.org/headers?a=test", Map.class);
    }

}
