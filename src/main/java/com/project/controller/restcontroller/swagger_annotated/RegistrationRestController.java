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

@Api(tags = "This is the REST-API documentation for the Registration page")
@RestController
@AllArgsConstructor
@RequestMapping("/registration")
public class RegistrationRestController {

    UserAccountService userAccountService;
    FormLoginErrorMessageService messageService;

    @ApiOperation(value = "Get RegistrationDTO, which is an object template, containing RegistrationUserDTO and FormLoginErrorMessageDTO"
            , response = RegistrationDTO.class, tags = "getRegistrationDTO")
    @GetMapping()
    public ResponseEntity<RegistrationDTO> getRegistrationDTO(){
        return new ResponseEntity<>(new RegistrationDTO(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<RegistrationDTO> createNewUserAccount(@ApiParam(value = " RegistrationUserDTO Model", required = true)@RequestBody@Valid RegistrationUserDTO user, BindingResult result, HttpServletRequest request){

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
            return new ResponseEntity<>(registrationDTO, HttpStatus.OK);
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            registrationDTO.setErrorMessage(messageService.getErrorMessageOnPasswordsDoesNotMatch());
            return new ResponseEntity<>(registrationDTO, HttpStatus.OK);
        }
        if (userAccountService.emailExist(user.getEmail())) {
            registrationDTO.setErrorMessage(messageService.getErrorMessageOnEmailUIndex());
            return new ResponseEntity<>(registrationDTO, HttpStatus.OK);
        }

        try {
            userAccountService.save(user, url.toString());
        } catch (DataIntegrityViolationException e) {
            if (e.getCause().getCause().getMessage().contains("login")) {
                registrationDTO.setErrorMessage(messageService.getErrorMessageOnLoginUIndex());
            } else {
                registrationDTO.setErrorMessage(messageService.getErrorMessageOnEmailUIndex());
            }
            return new ResponseEntity<>(registrationDTO, HttpStatus.OK);
        }
        registrationDTO.setErrorMessage(new FormLoginErrorMessageDTO(false, "success"));
        return new ResponseEntity<>(registrationDTO, HttpStatus.OK);


    }
}
