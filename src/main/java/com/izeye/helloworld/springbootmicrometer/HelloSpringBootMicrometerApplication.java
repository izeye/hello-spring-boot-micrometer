package com.izeye.helloworld.springbootmicrometer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HelloSpringBootMicrometerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringBootMicrometerApplication.class, args);
	}

}
