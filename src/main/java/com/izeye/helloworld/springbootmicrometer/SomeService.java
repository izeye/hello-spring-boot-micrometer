package com.izeye.helloworld.springbootmicrometer;

import java.util.Map;

public interface SomeService {

    Map<String, Object> invokeRestTemplateWithUri();

    Map<String, Object> invokeRestTemplateWithUriString();

    Map<String, Object> invokeRestTemplateWithUriTemplate();

}
