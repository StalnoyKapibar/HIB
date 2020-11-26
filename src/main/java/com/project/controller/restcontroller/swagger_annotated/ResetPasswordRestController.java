package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.EmailDTO;
import com.project.model.UserDTOResetPassword;
import com.project.service.abstraction.ResetPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "REST-API документ, описывающий сброс пароля пользователя")
@CrossOrigin(origins = "*")
@RestController
public class ResetPasswordRestController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @ApiOperation(value = "Проверка email на валидность и брос пароля"
            , notes = "Ендпойнт параметром принимает email пользователя. " +
                      "Ендпойнт возвращает json с результатом ответа(\"ok\", \"noEmail\", \"invalid format email\")"
            , response = EmailDTO.class
            , tags = "sendResetPassword")
    @PostMapping("/sendResetPassword")
    public ResponseEntity<EmailDTO> getEmail(@ApiParam(value = "email", required = true)
                                             @RequestBody EmailDTO email, HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(':')
                .append(request.getServerPort());
        return new ResponseEntity<>(
                new EmailDTO(resetPasswordService.sendEmailResetPassword(email.getEmail(), url.toString())), HttpStatus.OK);
    }

    @ApiOperation(value = "Сохранение аккаунта пользователя с новым паролем"
            , notes = "Ендпойнт параметром принимает объект: UserDTOResetPassword. " +
                      "Ендпойнт возвращает json с результатом ответа(\"ok\", \"noValid\", \"passError\")"
            , response = EmailDTO.class
            , tags = "createNewPassword")
    @PostMapping("/newPassword")
    public ResponseEntity<EmailDTO> getNewPassword(@ApiParam(value = " параметр типа: UserDTOResetPassword", required = true)
                                                   @RequestBody UserDTOResetPassword userDTOResetPassword) {
        return new ResponseEntity<>(new EmailDTO(resetPasswordService.saveNewPasswordReset(userDTOResetPassword)), HttpStatus.OK);
    }
}
