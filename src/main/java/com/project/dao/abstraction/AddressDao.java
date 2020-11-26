package com.project.dao.abstraction;

import com.project.model.Address;
import com.project.model.AddressDTO;
import com.project.model.UserAccount;

/**
 * Represented dao for interaction with {@link Address} entity.
 * Note that {@link UserAccount} must have field " Set<Address>" with getter and setter.
 */

public interface AddressDao extends GenericDao<Long, Address> {
    /**
     * Build address with addressDTO data
     *
     * @param addressDTO with data
     * @return created address
     */
    Address getAddressFromAddressDTO(AddressDTO addressDTO);

    /**
     * Set new address for user
     *
     * @param userAccount
     * @param address
     * @return new address
     */
    Address addAddressToUser(UserAccount userAccount, Address address);
}
