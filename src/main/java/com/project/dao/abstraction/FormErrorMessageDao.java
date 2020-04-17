package com.project.dao.abstraction;

import com.project.model.FormLoginErrorMessage;
import com.project.model.FormLoginErrorMessageDTO;

public interface FormErrorMessageDao extends GenericDao<Long, FormLoginErrorMessage> {
    FormLoginErrorMessageDTO getErrorMessage(String field, String reason, String locale);
}
