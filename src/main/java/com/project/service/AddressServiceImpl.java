package com.project.service;

import com.project.dao.abstraction.AddressDao;
import com.project.model.AddressDto;
import com.project.service.abstraction.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private AddressDao addressDAO;

    @Override
    public void addAddress(AddressDto addressDTO) {
        addressDAO.add(addressDAO.getAddressFromAddressDTO(addressDTO));
    }
}
