package com.izeye.helloworld.springbootmicrometer;

import org.eclipse.jetty.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * {@link ApplicationRunner} for testing.
 *
 * @author Johnny Lim
 */
@Component
public class TestRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestRunner.class);

    private final HttpClient httpClient;

    public TestRunner(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String response = this.httpClient.GET("https://httpbin.org/get").getContentAsString();
        log.info("Response: {}", response);
    }

}
