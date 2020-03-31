package com.project.service;

import com.project.dao.AddressDAO;
import com.project.model.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressDAO addressDAO;

    @Override
    public void addAddress(AddressDTO addressDTO) {
        addressDAO.addAddress(addressDTO);
    }
}
