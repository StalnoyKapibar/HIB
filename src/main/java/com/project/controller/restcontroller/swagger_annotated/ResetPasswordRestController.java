package com.project.controller.restcontroller.swagger_annotated;

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
            , notes = "Ендпойнт возвращает строку с результатом ответа(\"ok\", \"noEmail\", \"invalid format email\")"
            , response = Map.class
            , tags = "sendResetPassword")
    @PostMapping("/sendResetPassword")
    public ResponseEntity<Map<String, String>> getEmail(@ApiParam(value = "email", required = true)@RequestBody Map<String, String> email, HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(':')
                .append(request.getServerPort());
        Map<String,String> map = new HashMap<>();
        map.put("email", resetPasswordService.sendEmailResetPassword(email.get("email"), url.toString()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "Сохранение аккаунта пользователя с новым паролем"
            , notes = "Ендпойнт возвращает строку с результатом ответа(\"ok\", \"noValid\", \"passError\")"
            , response = Map.class
            , tags = "createNewPassword")
    @PostMapping("/newPassword")
    public ResponseEntity<Map<String, String>> getNewPassword(@ApiParam(value = " параметр типа: UserDTOResetPassword", required = true)@RequestBody UserDTOResetPassword userDTOResetPassword) {
        Map<String,String> map = new HashMap<>();
        map.put("email", resetPasswordService.saveNewPasswordReset(userDTOResetPassword));
      return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
