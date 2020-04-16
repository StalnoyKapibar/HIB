package com.project.dao;

import com.project.dao.GenericDAO.AbstractDAO;
import com.project.model.Address;
import com.project.model.AddressDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AddressDAOImpl extends AbstractDAO<Long, Address> implements AddressDAO {

    AddressDAOImpl(){
        super(Address.class);
    }

    @Override
    public Address getAddressFromAddressDTO(AddressDTO addressDTO) {
        return Address.builder()
                .flat(addressDTO.getFlat())
                .house(addressDTO.getHouse())
                .street(addressDTO.getStreet())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .postalCode(addressDTO.getPostalCode())
                .country(addressDTO.getCountry())
                .lastName(addressDTO.getLastName())
                .firstName(addressDTO.getFirstName())
                .build();
    }
}
