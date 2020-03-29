package com.project.controller.restcontroller;

import com.project.model.BookDTO;
import com.project.model.UserAccount;
import com.project.model.UserDTO;
import com.project.service.UserAccountServiceImpl;
import com.project.service.UserDetailServiceImpl;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class CabinetRController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @GetMapping("/cabinet/getAU")
    public UserDTO getAU() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = userService.getUserDTOByLogin(auth.getName());
        return userDTO;
    }

    @PostMapping("/cabinet/savePersonalInformation")
    public String savePersonalInformation(@RequestBody UserDTO userDTO) {
        if (!userService.checkEmailFromOtherUsers(userDTO.getEmail(), userDTO.getUserId())) {
            return "error";
        } else {
            userService.saveUserDTOPersonalInformation(userDTO);
            return "ok";
        }
    }

    @PostMapping("/cabinet/savePassword")
    public void savePassword(@RequestBody UserDTO userDTO) {
        String tmpPasswordEncode = encoder.encode(userDTO.getPassword());
        userDTO.setPassword(tmpPasswordEncode);
        userService.saveUserDTOPassword(userDTO);
    }
}
