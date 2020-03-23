package com.project.dao;

import com.project.model.FormLoginErrorMessage;
import com.project.model.FormLoginErrorMessageDTO;

public interface FormErrorMessageDAO {
    FormLoginErrorMessageDTO getErrorMessage(String field, String reason, String locale);

    void save(FormLoginErrorMessage errorMessage);
}
