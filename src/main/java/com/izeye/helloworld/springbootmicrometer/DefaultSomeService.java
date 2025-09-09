package com.izeye.helloworld.springbootmicrometer;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Service
public class DefaultSomeService implements SomeService {

    private final RestTemplate restTemplate;

    public DefaultSomeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Map<String, Object> invokeRestTemplateWithUri() {
        return this.restTemplate.exchange(URI.create("https://httpbin.org/get?query=apple"), HttpMethod.GET, null, Map.class).getBody();
    }

    @Override
    public Map<String, Object> invokeRestTemplateWithUriString() {
        return this.restTemplate.exchange("https://httpbin.org/get?query=apple", HttpMethod.GET, null, Map.class).getBody();
    }

    @Override
    public Map<String, Object> invokeRestTemplateWithUriTemplate() {
        return this.restTemplate.exchange("https://httpbin.org/{path}?query={query}", HttpMethod.GET, null, Map.class, "get", "apple").getBody();
    }

}
