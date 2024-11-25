package com.izeye.helloworld.springbootmicrometer;

import org.springframework.cache.annotation.Cacheable;

/**
 * Service interface for {@link Person}.
 *
 * @author Johnny Lim
 */
public interface PersonService {

    @Cacheable("persons")
    Person getPerson(String username);

}
