package com.project.controller.restcontroller;

import com.project.model.UserDTO;
import com.project.model.UserDTONewPassword;
import com.project.service.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class CabinetRController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/current-user")
    public UserDTO getAU() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isOauth2Acc;
        Class<? extends Authentication> authenticationClass = auth.getClass();
        String className = authenticationClass.getName();
        isOauth2Acc = className.equals("org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken");
        UserDTO userDTO = userService.getUserDTOByEmail(auth.getName(), isOauth2Acc);
        return userDTO;
    }

    @PostMapping("/cabinet/savePersonalInformation")
    public String savePersonalInformation(@RequestBody UserDTO userDTO) {
        return userService.saveUserDTOPersonalInformation(userDTO);
    }

    @PostMapping("/cabinet/savePassword")
    public String savePassword(@RequestBody UserDTONewPassword userDTONewPassword) {
        return userService.saveUserDTOPassword(userDTONewPassword);
    }
}
