package com.project.dao;

import com.project.dao.abstraction.AddressDao;
import com.project.model.Address;
import com.project.model.AddressDto;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl extends AbstractDao<Long, Address> implements AddressDao {

    AddressDaoImpl(){
        super(Address.class);
    }

    @Override
    public Address getAddressFromAddressDTO(AddressDto addressDTO) {
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
