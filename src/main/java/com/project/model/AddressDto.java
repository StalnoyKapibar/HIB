package com.project.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {
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
