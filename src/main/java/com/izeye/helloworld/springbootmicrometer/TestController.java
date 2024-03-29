package com.izeye.helloworld.springbootmicrometer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * {@link RestController} for testing.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/test")
public class TestController {

    private final TestService service;

    public TestController(TestService service) {
        this.service = service;
    }

    @GetMapping(path = "rest-template")
    public Map<String, Object> testRestTemplate() {
        return this.service.testRestTemplate();
    }

}
