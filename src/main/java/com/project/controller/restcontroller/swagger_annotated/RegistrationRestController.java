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

    @ApiOperation(value = "Get RegistrationDTO"
            , notes = "Get RegistrationDTO, which is an object template, containing RegistrationUserDTO and FormLoginErrorMessageDTO." +
            "You need to go to the user/user-page.html page."
            , response = RegistrationDTO.class
            , tags = "getRegistrationDTO")
    @GetMapping()
    public ResponseEntity<RegistrationDTO> getRegistrationDTO(){
        return new ResponseEntity<>(
                new RegistrationDTO(new RegistrationUserDTO(),new FormLoginErrorMessageDTO(false, ""))
                , HttpStatus.OK);
    }
    @ApiOperation(value = "Create new UserAccount"
            , notes = "When receiving invalid data in an object RegistrationUserDTO, the method returns error message object."+
            " If the server response code is 201, you need to go to the requestApproveAuth.html page"
            , response = RegistrationDTO.class
            , tags = "createNewUserAccount")
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

    @ApiOperation(value = "Get registration in one click"
            , notes = "Get RegistrationDTO, which is a template object containing RegistrationUserDTO and FormLoginErrorMessageDTO." +
            " This is one click registration, you need to go to the cabinet.html page."
            , response = RegistrationDTO.class
            , tags = "getOneClickRegistration")
    @GetMapping("/1clickreg")
    public ResponseEntity<RegistrationDTO> get1ClickRegistrationPage(){
        return new ResponseEntity<>(
                new RegistrationDTO(new RegistrationUserDTO(),new FormLoginErrorMessageDTO(false, ""))
                , HttpStatus.OK);
    }
}
