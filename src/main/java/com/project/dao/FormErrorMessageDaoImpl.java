package com.project.dao;

import com.project.dao.abstraction.FormErrorMessageDao;
import com.project.model.FormLoginErrorMessage;
import com.project.model.FormLoginErrorMessageDto;
import org.springframework.stereotype.Repository;

@Repository
public class FormErrorMessageDaoImpl extends AbstractDao<Long, FormLoginErrorMessage> implements FormErrorMessageDao {
    FormErrorMessageDaoImpl(){super(FormLoginErrorMessage.class);}

    @Override
    public FormLoginErrorMessageDto getErrorMessage(String field, String reason, String locale) {
        String HQL = "SELECT new com.project.model.FormLoginErrorMessageDTO(true, fem.body.LOCALE) ".replaceAll("LOCALE", locale) +
                     "FROM FormLoginErrorMessage fem " +
                     "WHERE fem.field=:field AND fem.reason=:reason";
        FormLoginErrorMessageDto singleResult = entityManager.createQuery(HQL, FormLoginErrorMessageDto.class)
                .setParameter("field", field)
                .setParameter("reason", reason).getSingleResult();
        return singleResult;
    }
}
