package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.UserDTO;
import com.project.model.UserDTONewPassword;
import com.project.service.abstraction.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Api(tags="REST-API документ, " +
        "описывающий сервис взаимодействия с данными авторизованнго пользователя")
@RestController
public class CabinetRController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Возвращает текущего авторизованного пользователя",
            response = UserDTO.class)
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

    @ApiOperation(value = "Сохранить информацию о пользователе",
            notes = "Эндпойнт получает userDTO типа UserDTO и возвращает ok/error/synError")
    @PostMapping("/cabinet/savePersonalInformation")
    public String savePersonalInformation(@ApiParam(value = "userDTO", required = true) @RequestBody UserDTO userDTO) {
        return userService.saveUserDTOPersonalInformation(userDTO);
    }

    @ApiOperation(value = "Сохранить пароль пользователя",
            notes = "Эндпойнт получает параметр userDTONewPassword типа UserDTONewPassword и возвращает passOk/wrongPassword/passError")
    @PostMapping("/cabinet/savePassword")
    public String savePassword(@ApiParam(value = "userDTONewPassword", required = true)@RequestBody UserDTONewPassword userDTONewPassword) {
        return userService.saveUserDTOPassword(userDTONewPassword);
    }
}
