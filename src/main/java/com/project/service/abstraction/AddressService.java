package com.project.service.abstraction;

import com.project.model.Address;
import com.project.model.AddressDTO;
import com.project.model.UserAccount;

import java.util.Set;

public interface AddressService {
    Address addAddress(UserAccount user, Address addressDTO);

    AddressDTO updateAddresses(UserAccount user, AddressDTO address);

    Set<Address> getAddressByUserId(Long id);
}
