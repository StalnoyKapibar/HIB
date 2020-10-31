package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.Address;
import com.project.model.AddressDTO;
import com.project.model.UserAccount;
import com.project.service.abstraction.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Represented rest controller for interaction with {@link Address} entity.
 * Note that {@link UserAccount} must have field " Set<Address>" with getter and setter.
 */

@Api(tags = "REST-API документ, описывающий взаимодействие с сервисом: адрес пользователя")
@RestController
@AllArgsConstructor
@RequestMapping("/api/user/address")
public class AddressRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressRestController.class.getName());
    private AddressService addressService;

    /**
     * Get users addresses
     *
     * @param user
     * @return users addresses
     */

    @ApiOperation(value = "Получить все адреса авторизованного пользователя"
            , notes = "Ендпойнт возвращает все адреса пользователя"
            , response = Address.class
            , responseContainer  =  "Set"
            , tags = "getUserAddresses")
    @GetMapping
    public Set<Address> getUserAddresses(@AuthenticationPrincipal UserAccount user) {
        return addressService.getAddressByUserId(user.getId());
    }

    /**
     * Set new address for user
     *
     * @param user
     * @param address
     * @return new address
     */

    @ApiOperation(value = "Установить новый адрес пользователю"
            , notes = "Ендпойнт возвращает новый адрес пользователя"
            , response = Address.class
            , tags = "createNewAddressToUser")
    @PostMapping
    public Address createNewAddress(@AuthenticationPrincipal UserAccount user,
                                    @ApiParam(value = "новый адрес пользователя", required = true)
                                    @RequestBody Address address) {
        LOGGER.info("POST request '/api/user/address' user {} with {}", user, address);
        return addressService.addAddress(user, address);
    }

    /**
     * Change users address
     *
     * @param user
     * @param address - addressDTO with new data
     * @return incoming addressDTO
     */

    @ApiOperation(value = "Изменить адрес пользователя"
            , notes = "Ендпойнт возвращает новый адрес пользователя"
            , response = AddressDTO.class
            , tags = "updateAddressToUser")
    @PutMapping
    public AddressDTO updateToUser(@AuthenticationPrincipal UserAccount user,
                                   @ApiParam(value = "новый адрес пользователя", required = true)
                                   @RequestBody AddressDTO address) {
        LOGGER.info("PUT request '/api/user/address' from {} with {}", user, address);
        return addressService.updateAddresses(user, address);
    }
}
