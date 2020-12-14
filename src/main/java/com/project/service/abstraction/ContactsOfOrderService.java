package com.project.service.abstraction;

import com.project.model.ContactsOfOrder;

import java.util.List;

public interface ContactsOfOrderService {

    void update (ContactsOfOrder contactsOfOrder);

    List<ContactsOfOrder> findByPhone(String phone);
}
