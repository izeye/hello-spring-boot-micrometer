package com.izeye.helloworld.springbootmicrometer;

import com.timgroup.statsd.StatsDClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Sample {@link ApplicationRunner}.
 *
 * @author Johnny Lim
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    private final StatsDClient statsDClient;

    public MyApplicationRunner(StatsDClient statsDClient) {
        this.statsDClient = statsDClient;
    }

    @Override
    public void run(ApplicationArguments args) {
        Thread thread = new Thread(() -> {
            while (true) {
                this.statsDClient.count("my.counter", 1L);
                sleep();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(10);
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

}
