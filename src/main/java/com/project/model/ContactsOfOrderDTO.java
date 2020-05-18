package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactsOfOrderDTO {

    private long id;
    private String email;
    private String phone;
    private String comment;

    public ContactsOfOrderDTO(ContactsOfOrder contacts) {
        this.id = contacts.getId();
        this.phone = contacts.getPhone();
        this.email = contacts.getEmail();
    }

}

