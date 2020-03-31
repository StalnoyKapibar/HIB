package com.project.controller.restcontroller;

import com.project.model.AddressDTO;
import com.project.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddAddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/enterAddress")
    public String addAddress(@RequestBody AddressDTO addressDTO) {
        addressService.addAddress(addressDTO);
        return null;
    }
}
