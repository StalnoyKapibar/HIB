package com.project.dao.abstraction;

import com.project.model.Address;
import com.project.model.AddressDto;

public interface AddressDao extends GenericDao<Long, Address> {
    Address getAddressFromAddressDTO(AddressDto addressDTO);
}
