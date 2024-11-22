package com.izeye.helloworld.springbootmicrometer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * {@link RestController} for testing.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping(path = "/sleep")
    public String sleep(@RequestParam int seconds) throws InterruptedException {
        TimeUnit.SECONDS.sleep(seconds);
        return "Slept for %d seconds!".formatted(seconds);
    }

}
