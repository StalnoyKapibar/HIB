package com.project.service.abstraction;

import com.project.model.Address;
import com.project.model.AddressDTO;
import com.project.model.UserAccount;

import java.util.Set;

/**
 * Represented service for interaction with {@link Address} entity.
 * Note that {@link UserAccount} must have field " Set<Address>" with getter and setter.
 */
public interface AddressService {

    /**
     * Set new address for user
     *
     * @param user
     * @param address
     * @return new address
     */
    Address addAddress(UserAccount user, Address address);

    /**
     * Change users address
     *
     * @param user
     * @param address - addressDTO with new data
     * @return incoming addressDTO
     */
    AddressDTO updateAddresses(UserAccount user, AddressDTO address);

    /**
     * Get users addresses by user id
     *
     * @param id - user id
     * @return users addresses
     */
    Set<Address> getAddressByUserId(Long id);
}
