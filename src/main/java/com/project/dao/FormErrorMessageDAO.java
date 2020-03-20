package com.project.dao;

import com.project.model.FormLoginErrorMessageDTO;

public interface FormErrorMessageDAO {
    FormLoginErrorMessageDTO getErrorMessage(String field, String reason, String locale);
}
