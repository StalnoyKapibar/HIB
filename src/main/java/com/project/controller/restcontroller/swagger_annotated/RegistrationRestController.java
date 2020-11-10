package com.project.controller.restcontroller.swagger_annotated;

import com.project.model.Book;
import com.project.model.FormLoginErrorMessageDTO;
import com.project.model.RegistrationDTO;
import com.project.model.RegistrationUserDTO;
import com.project.service.abstraction.FormLoginErrorMessageService;
import com.project.service.abstraction.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "REST-API документ, описывающий сервис регистрации пользователя")
@RestController
@AllArgsConstructor
@RequestMapping("/registration")
public class RegistrationRestController {

    UserAccountService userAccountService;
    FormLoginErrorMessageService messageService;

    @ApiOperation(value = "Получить шаблон объекта RegistrationDTO"
            , notes = "Ендпойнт вернёт RegistrationDTO, который является шаблоном объекта" +
            ", содержащим RegistrationUserDTO и FormLoginErrorMessageDTO."
            , response = RegistrationDTO.class
            , tags = "getRegistrationDTO")
    @GetMapping()
    public ResponseEntity<RegistrationDTO> getRegistrationDTO(){
        return new ResponseEntity<>(
                new RegistrationDTO(new RegistrationUserDTO(),new FormLoginErrorMessageDTO(false, ""))
                , HttpStatus.OK);
    }
    @ApiOperation(value = "Создать новый аккаунт пользователя"
            , notes = "Ендпойнт возвращает объект RegistrationDTO" +
            ", содержащий объекты RegistrationUserDTO и FormLoginErrorMessageDTO. " +
            "При получении невалидных данных в объекте RegistrationUserDTO" +
            ", объект  FormLoginErrorMessageDTO получит соответственное сообщение об ошибке."
            , response = RegistrationDTO.class
            , tags = "createNewUserAccount")
    @PostMapping()
    @CrossOrigin()
    public ResponseEntity<RegistrationDTO> createNewUserAccount(
            @ApiParam(value = " RegistrationUserDTO Model", required = true)
            @RequestBody@Valid RegistrationUserDTO user, BindingResult result, HttpServletRequest request){


        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setUser(user);

        StringBuilder url = new StringBuilder();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(':')
                .append(request.getServerPort());
        if (result.hasErrors()) {
            registrationDTO.setErrorMessage(messageService.getErrorMessage(result));
            return new ResponseEntity<>(registrationDTO, HttpStatus.BAD_REQUEST);
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            registrationDTO.setErrorMessage(messageService.getErrorMessageOnPasswordsDoesNotMatch());
            return new ResponseEntity<>(registrationDTO, HttpStatus.BAD_REQUEST);
        }
        if (userAccountService.emailExist(user.getEmail())) {
            registrationDTO.setErrorMessage(messageService.getErrorMessageOnEmailUIndex());
            return new ResponseEntity<>(registrationDTO, HttpStatus.BAD_REQUEST);
        }

        try {
            userAccountService.save(user, url.toString());
        } catch (DataIntegrityViolationException e) {
            if (e.getCause().getCause().getMessage().contains("login")) {
                registrationDTO.setErrorMessage(messageService.getErrorMessageOnLoginUIndex());
            } else {
                registrationDTO.setErrorMessage(messageService.getErrorMessageOnEmailUIndex());
            }
            return new ResponseEntity<>(registrationDTO, HttpStatus.BAD_REQUEST);
        }
        registrationDTO.setErrorMessage(new FormLoginErrorMessageDTO(false, "success"));
        return new ResponseEntity<>(registrationDTO, HttpStatus.CREATED);


    }

    @ApiOperation(value = "Регистрация в один клик"
            , notes = "Ендпойнт вернёт RegistrationDTO, объект шаблона" +
            ", содержащий RegistrationUserDTO и FormLoginErrorMessageDTO."
            , response = RegistrationDTO.class
            , tags = "getOneClickRegistration")
    @GetMapping("/1clickreg")
    public ResponseEntity<RegistrationDTO> get1ClickRegistrationPage(){
        return new ResponseEntity<>(
                new RegistrationDTO(new RegistrationUserDTO(),new FormLoginErrorMessageDTO(false, ""))
                , HttpStatus.OK);
    }
}
