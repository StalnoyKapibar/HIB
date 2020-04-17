package com.project.controller.restcontroller;

import com.project.model.UserDtoResetPassword;
import com.project.service.abstraction.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/sendResetPassword")
    public String getEmail(@RequestBody String email) {
        return resetPasswordService.sendEmailResetPassword(email);
    }

    @PostMapping("/newPassword")
    public String getNewPassword(@RequestBody UserDtoResetPassword userDTOResetPassword) {
      return resetPasswordService.saveNewPasswordReset(userDTOResetPassword);
    }
}
