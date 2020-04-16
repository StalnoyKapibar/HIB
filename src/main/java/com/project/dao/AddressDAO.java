package com.project.dao;

import com.project.dao.GenericDAO.IGenericDao;
import com.project.model.Address;
import com.project.model.AddressDTO;

public interface AddressDAO extends IGenericDao<Long, Address> {
    Address getAddressFromAddressDTO(AddressDTO addressDTO);
}
