package com.izeye.helloworld.springbootmicrometer;

import java.util.Map;

/**
 * Service interface for testing.
 *
 * @author Johnny Lim
 */
public interface TestService {

    Map<String, Object> testRestTemplateWithUri();

    Map<String, Object> testRestTemplateWithUrlTemplate();

    Map<String, Object> testRestTemplateWithUrl();

}
