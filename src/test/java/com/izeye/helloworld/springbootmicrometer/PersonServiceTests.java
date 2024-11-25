package com.izeye.helloworld.springbootmicrometer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PersonService}.
 *
 * @author Johnny Lim
 */
@SpringBootTest
@DirtiesContext
class PersonServiceTests {

    @Autowired
    PersonService personService;

    @Test
    void getPersonWithCacheable() {
        String username = "izeye";
        assertThat(this.personService.getPerson(username)).isSameAs(this.personService.getPerson(username));
    }

}
