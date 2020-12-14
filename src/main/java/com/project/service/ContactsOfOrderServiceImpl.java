package com.project.service;

import com.project.dao.ContactsOdOrderDaoImpl;
import com.project.model.ContactsOfOrder;
import com.project.service.abstraction.ContactsOfOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ContactsOfOrderServiceImpl implements ContactsOfOrderService {

    private ContactsOdOrderDaoImpl contactsOfOrderDao;

    @Override
    public void update(ContactsOfOrder contactsOfOrder) {
        contactsOfOrderDao.update(contactsOfOrder);
    }

    public List<ContactsOfOrder> findByPhone(String phone) {
        return contactsOfOrderDao.findByPhone(phone);
    }
}
