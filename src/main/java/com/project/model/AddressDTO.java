package com.project.model;

import lombok.*;

/**
 * Represented DTO for entity Address.
 * Keep same data
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDTO {
    private long id;
    private String flat;
    private String house;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String lastName;
    private String firstName;
}
