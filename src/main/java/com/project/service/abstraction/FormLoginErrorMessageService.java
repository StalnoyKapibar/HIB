package com.project.service.abstraction;

import com.project.model.FormLoginErrorMessage;
import com.project.model.FormLoginErrorMessageDto;
import org.springframework.validation.BindingResult;

public interface FormLoginErrorMessageService {
    FormLoginErrorMessageDto getErrorMessage(BindingResult result);

    FormLoginErrorMessageDto getErrorMessageOnPasswordsDoesNotMatch();

    FormLoginErrorMessageDto getErrorMessageOnLoginUIndex();

    FormLoginErrorMessageDto getErrorMessageOnEmailUIndex();

    FormLoginErrorMessageDto getErrorMessageOnNoValuePresent();

    FormLoginErrorMessageDto getErrorMessageOnBadCredential();

    void saveErrorMessage(FormLoginErrorMessage errorMessage);
}
