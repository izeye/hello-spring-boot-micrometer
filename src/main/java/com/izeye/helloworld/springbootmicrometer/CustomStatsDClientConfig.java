package com.izeye.helloworld.springbootmicrometer;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link Configuration} for a custom StatsD client.
 *
 * @author Johnny Lim
 */
@Configuration
public class CustomStatsDClientConfig {

    @Bean
    public NonBlockingStatsDClient customStatsDClient() {
        return new NonBlockingStatsDClientBuilder().prefix("prefix").hostname("localhost").port(8125).build();
    }

}
