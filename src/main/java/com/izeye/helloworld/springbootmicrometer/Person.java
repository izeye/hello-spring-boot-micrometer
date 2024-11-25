package com.izeye.helloworld.springbootmicrometer;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Person.
 *
 * @author Johnny Lim
 */
@Data
@AllArgsConstructor
public class Person {

    private String username;
    private String firstName;
    private String lastName;

}
