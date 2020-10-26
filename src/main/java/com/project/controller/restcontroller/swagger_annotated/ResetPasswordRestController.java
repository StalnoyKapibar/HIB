package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.RegistrationDTO;
import com.project.service.abstraction.ResetPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "This is the REST-API documentation for the Reset Password for user-page")
@RestController
@AllArgsConstructor
@RequestMapping("/resetPassword")
public class ResetPasswordRestController {

    ResetPasswordService resetPasswordService;

    @ApiOperation(value = "Reset the password"
            , notes = "If the password is successfully reset, a 200 response code will be returned. Otherwise, an AccessDeniedException (\"403\") will be thrown."
            , response = Void.class
            , tags = "getResetPassword")
    @GetMapping()
    public ResponseEntity<Void> resetPassword(@ApiParam(value = "token", required = true)@RequestParam("token") String token) {
        if (resetPasswordService.checkTokenResetPassword(token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new AccessDeniedException("403");
        }
    }
}
