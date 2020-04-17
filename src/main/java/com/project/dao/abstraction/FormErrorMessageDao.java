package com.project.dao.abstraction;

import com.project.model.FormLoginErrorMessage;
import com.project.model.FormLoginErrorMessageDto;

public interface FormErrorMessageDao extends GenericDao<Long, FormLoginErrorMessage> {
    FormLoginErrorMessageDto getErrorMessage(String field, String reason, String locale);
}
