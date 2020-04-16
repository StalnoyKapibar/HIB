package com.project.dao.abstraction;

import com.project.dao.abstraction.IGenericDao;
import com.project.model.Address;
import com.project.model.AddressDTO;

public interface AddressDao extends IGenericDao<Long, Address> {
    Address getAddressFromAddressDTO(AddressDTO addressDTO);
}
