package com.project.controller.restcontroller;

import com.project.model.UserDto;
import com.project.model.UserDtoNewPassword;
import com.project.service.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class CabinetRController {

    @Autowired
    private UserService userService;

    @GetMapping("/cabinet/getAU")
    public UserDto getAU() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDTO = userService.getUserDTOByLogin(auth.getName());
        return userDTO;
    }

    @PostMapping("/cabinet/savePersonalInformation")
    public String savePersonalInformation(@RequestBody UserDto userDTO) {
        return userService.saveUserDTOPersonalInformation(userDTO);
    }

    @PostMapping("/cabinet/savePassword")
    public String savePassword(@RequestBody UserDtoNewPassword userDTONewPassword) {
        return userService.saveUserDTOPassword(userDTONewPassword);
    }
}
