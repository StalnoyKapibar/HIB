package com.project.service;

import com.project.dao.abstraction.AddressDao;
import com.project.model.Address;
import com.project.model.AddressDTO;
import com.project.model.UserAccount;
import com.project.service.abstraction.AddressService;
import com.project.service.abstraction.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private UserAccountService userAccountService;
    private AddressDao addressDao;

    @Transactional
    @Override
    public Address addAddress(UserAccount user, Address address) {
        return addressDao.addAddressToUser(user, address);
    }

    @Override
    public AddressDTO updateAddresses(UserAccount user, AddressDTO address) {
        return null;
    }

    @Override
    public Set<Address> getAddressByUserId(Long id) {
        return userAccountService.getUserById(id).getAddresses();
    }
}
