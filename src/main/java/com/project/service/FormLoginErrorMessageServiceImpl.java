package com.project.service;

import com.project.dao.FormErrorMessageDaoImpl;
import com.project.model.FormLoginErrorMessage;
import com.project.model.FormLoginErrorMessageDTO;
import com.project.service.abstraction.FormLoginErrorMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class FormLoginErrorMessageServiceImpl implements FormLoginErrorMessageService {

    FormErrorMessageDaoImpl errorMessageDAO;

    HttpSession httpSession;

    //Common method to get standard error message by BindingResult (using in registration form)
    @Override
    public FormLoginErrorMessageDTO getErrorMessage(BindingResult result) {
        String reason = result.getFieldError().getCodes()[3]; // reason of validation error
        String field = result.getFieldError().getField(); // in witch field
        return errorMessageDAO.getErrorMessage(field, reason, getCurrentLocale());
    }

    @Override
    public FormLoginErrorMessageDTO getErrorMessageOnPasswordsDoesNotMatch() {
        return errorMessageDAO.getErrorMessage("password", "DontMatch", getCurrentLocale());
    }

    @Override
    public FormLoginErrorMessageDTO getErrorMessageOnEmailUIndex() {
        return errorMessageDAO.getErrorMessage("email", "UIndex", getCurrentLocale());
    }

    @Override
    public FormLoginErrorMessageDTO getErrorMessageOnNoValuePresent() {
        return errorMessageDAO.getErrorMessage("auth", "NoValuePresent", getCurrentLocale());
    }

    @Override
    public FormLoginErrorMessageDTO getErrorMessageOnBadCredential() {
        return errorMessageDAO.getErrorMessage("auth", "BadCredential", getCurrentLocale());
    }

    @Override
    public FormLoginErrorMessageDTO getErrorMessageOnUserDisabled() {
        return errorMessageDAO.getErrorMessage("auth", "UserDisabled", getCurrentLocale());
    }

    @Override
    public void saveErrorMessage(FormLoginErrorMessage errorMessage) {
        errorMessageDAO.add(errorMessage);
    }

    @Override
    public FormLoginErrorMessageDTO getErrorMessageOnLoginUIndex() {
        return errorMessageDAO.getErrorMessage("login", "UIndex", getCurrentLocale());
    }

    private String getCurrentLocale() {
        return Optional.of(httpSession.getAttribute("LANG").toString()).orElse("en");
    }
}
