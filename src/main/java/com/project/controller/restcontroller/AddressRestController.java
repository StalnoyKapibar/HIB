package com.project.controller.restcontroller;

import com.project.model.Address;
import com.project.model.AddressDTO;
import com.project.model.UserAccount;
import com.project.service.abstraction.AddressService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/address")
public class AddressRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressRestController.class.getName());
    private AddressService addressService;

    @GetMapping
    public Set<Address> getUserAddresses(@AuthenticationPrincipal UserAccount user) {
        return addressService.getAddressByUserId(user.getId());
    }

    @PostMapping
    public Address createNewAddress(@AuthenticationPrincipal UserAccount user, @RequestBody Address address) {
        LOGGER.info("POST request '/api/user/address' user {} with {}", user, address);
        return addressService.addAddress(user, address);
    }

    @PutMapping
    public AddressDTO updateToUser(@AuthenticationPrincipal UserAccount user, AddressDTO address) {
        LOGGER.info("PUT request '/api/user/address' from {} with {}", user, address);
        return addressService.updateAddresses(user, address);
    }
}
