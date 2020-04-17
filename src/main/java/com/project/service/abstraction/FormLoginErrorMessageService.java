package com.project.service.abstraction;

import com.project.model.FormLoginErrorMessage;
import com.project.model.FormLoginErrorMessageDTO;
import org.springframework.validation.BindingResult;

public interface FormLoginErrorMessageService {
    FormLoginErrorMessageDTO getErrorMessage(BindingResult result);

    FormLoginErrorMessageDTO getErrorMessageOnPasswordsDoesNotMatch();

    FormLoginErrorMessageDTO getErrorMessageOnLoginUIndex();

    FormLoginErrorMessageDTO getErrorMessageOnEmailUIndex();

    FormLoginErrorMessageDTO getErrorMessageOnNoValuePresent();

    FormLoginErrorMessageDTO getErrorMessageOnBadCredential();

    void saveErrorMessage(FormLoginErrorMessage errorMessage);
}
