package com.project.dao;

import com.project.model.Address;
import com.project.model.AddressDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AddressDAOImpl implements AddressDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setFlat(addressDTO.getFlat());
        address.setHouse(addressDTO.getHouse());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCountry(addressDTO.getCountry());
        address.setLastName(addressDTO.getLastName());
        address.setFirstName(addressDTO.getFirstName());
        entityManager.persist(address);
    }
}
