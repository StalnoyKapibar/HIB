package com.project.controller.restcontroller.swagger_annotated;

import com.project.service.abstraction.ResetPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "REST-API документ описывающий сброс пароля на странице 'user-page'")
@RestController
@AllArgsConstructor
@RequestMapping("/resetPassword")
public class ResetPasswordRestController {

    ResetPasswordService resetPasswordService;

    @ApiOperation(value = "Сброс пароля"
            , notes = "Если пароль успешно сброшен, будет возвращен код ответа 200 . В противном случае будет возвращен код ответа 403."
            , response = Void.class
            , tags = "getResetPassword")
    @GetMapping()
    public ResponseEntity<Void> resetPassword(
            @ApiParam(value = "token", required = true) @RequestParam("token") String token) {
        if (resetPasswordService.checkTokenResetPassword(token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
