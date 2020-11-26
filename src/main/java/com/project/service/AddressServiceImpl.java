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

/**
 * Represented service for interaction with {@link Address} entity.
 * Note that {@link UserAccount} must have field " Set<Address>" with getter and setter.
 */

@Service
@Transactional
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private UserAccountService userAccountService;
    private AddressDao addressDao;

    /**
     * Set new address for user
     *
     * @param user
     * @param address
     * @return new address
     */
    @Transactional
    @Override
    public Address addAddress(UserAccount user, Address address) {
        return addressDao.addAddressToUser(user, address);
    }

    /**
     * Change users address
     *
     * @param user
     * @param address - addressDTO with new data
     * @return incoming addressDTO
     */
    @Override
    public AddressDTO updateAddresses(UserAccount user, AddressDTO address) {
        return null;
    }

    /**
     * Get users addresses by user id
     *
     * @param id - user id
     * @return users addresses
     */
    @Override
    public Set<Address> getAddressByUserId(Long id) {
        return null;
    }
}
