package com.project.dao;

import com.project.dao.abstraction.FormErrorMessageDao;
import com.project.model.FormLoginErrorMessage;
import com.project.model.FormLoginErrorMessageDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FormErrorMessageDaoImpl extends AbstractDao<Long, FormLoginErrorMessage> implements FormErrorMessageDao {
    FormErrorMessageDaoImpl(){super(FormLoginErrorMessage.class);}

    @Override
    public FormLoginErrorMessageDTO getErrorMessage(String field, String reason, String locale) {
        // TODO: 09.07.2020  Блок if убрать, добавить запись в БД: auth, UserDisabled, message
        if (reason.equals("UserDisabled")) {
            return new FormLoginErrorMessageDTO(true, "Пожалуйста, подтвердите Ваш e-mail. Для этого перейдите по ссылке из письма.");
        }
        String HQL = "SELECT new com.project.model.FormLoginErrorMessageDTO(true, fem.body.LOCALE) ".replaceAll("LOCALE", locale) +
                     "FROM FormLoginErrorMessage fem " +
                     "WHERE fem.field=:field AND fem.reason=:reason";
        FormLoginErrorMessageDTO singleResult = entityManager.createQuery(HQL, FormLoginErrorMessageDTO.class)
                .setParameter("field", field)
                .setParameter("reason", reason).getSingleResult();
        return singleResult;
    }
}
