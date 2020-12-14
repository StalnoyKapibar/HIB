package com.project.dao;

import com.project.model.ContactsOfOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactsOdOrderDaoImpl extends AbstractDao <Long, ContactsOfOrder> {

    public ContactsOdOrderDaoImpl() {
        super(ContactsOfOrder.class);
    }


    public List<ContactsOfOrder> findByPhone(String phone) {
        return entityManager.createQuery("SELECT b FROM contacts b where phone=:phone", ContactsOfOrder.class).setParameter("phone" , phone).getResultList();
    }
}
