package com.project.dao.abstraction;

import com.project.model.Address;
import com.project.model.AddressDTO;
import com.project.model.UserAccount;

public interface AddressDao extends GenericDao<Long, Address> {
    Address getAddressFromAddressDTO(AddressDTO addressDTO);

    Address addAddressToUser(UserAccount userAccount, Address address);
}
