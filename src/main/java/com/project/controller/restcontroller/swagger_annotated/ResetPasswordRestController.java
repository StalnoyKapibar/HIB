package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.UserDTOResetPassword;
import com.project.service.abstraction.ResetPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "REST-API документ, описывающий сброс пароля пользователя")
@RestController
public class ResetPasswordRestController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @ApiOperation(value = "Проверка email на валидность и брос пароля"
            , notes = "Ендпойнт возвращает строку с результатом ответа(\"ok\", \"noEmail\", \"invalid format email\")"
            , response = String.class
            , tags = "sendResetPassword")
    @PostMapping("/sendResetPassword")
    public String getEmail(@ApiParam(value = "email", required = true)@RequestBody String email, HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(':')
                .append(request.getServerPort());
        return resetPasswordService.sendEmailResetPassword(email, url.toString());
    }

    @ApiOperation(value = "Сохранение аккаунта пользователя с новым паролем"
            , notes = "Ендпойнт возвращает строку с результатом ответа(\"ok\", \"noValid\", \"passError\")"
            , response = String.class
            , tags = "createNewPassword")
    @PostMapping("/newPassword")
    public String getNewPassword(@ApiParam(value = " параметр типа: UserDTOResetPassword", required = true)@RequestBody UserDTOResetPassword userDTOResetPassword) {
      return resetPasswordService.saveNewPasswordReset(userDTOResetPassword);
    }
}
