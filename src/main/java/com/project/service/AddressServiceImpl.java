package com.project.service;

import com.project.dao.AddressDAO;
import com.project.model.AddressDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private AddressDAO addressDAO;

    @Override
    public void addAddress(AddressDTO addressDTO) {
        addressDAO.add(addressDAO.getAddressFromAddressDTO(addressDTO));
    }
}
