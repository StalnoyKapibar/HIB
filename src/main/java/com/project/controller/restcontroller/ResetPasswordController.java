package com.project.controller.restcontroller;

import com.project.model.UserDTONewPassword;
import com.project.model.UserDTOResetPassword;
import com.project.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.net.URI;

@RestController
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/sendResetPassword")
    public String getEmail(@RequestBody String email) {
        return resetPasswordService.sendEmailResetPassword(email);
    }

    @PostMapping("/newPassword")
    public String getNewPassword(@RequestBody UserDTOResetPassword userDTOResetPassword) {
      return resetPasswordService.saveNewPasswordReset(userDTOResetPassword);
    }
}
