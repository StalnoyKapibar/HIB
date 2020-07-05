package com.project.controller.restcontroller;

import com.project.model.UserDTOResetPassword;
import com.project.service.abstraction.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/sendResetPassword")
    public String getEmail(@RequestBody String email, HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(':')
                .append(request.getServerPort());
        return resetPasswordService.sendEmailResetPassword(email, url.toString());
    }

    @PostMapping("/newPassword")
    public String getNewPassword(@RequestBody UserDTOResetPassword userDTOResetPassword) {
      return resetPasswordService.saveNewPasswordReset(userDTOResetPassword);
    }
}
