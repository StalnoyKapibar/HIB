package com.project.dao;

import com.project.dao.abstraction.FormErrorMessageDao;
import com.project.model.FormLoginErrorMessage;
import com.project.model.FormLoginErrorMessageDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FormErrorMessageDaoImpl implements FormErrorMessageDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public FormLoginErrorMessageDTO getErrorMessage(String field, String reason, String locale) {
        String HQL = "SELECT new com.project.model.FormLoginErrorMessageDTO(true, fem.body.LOCALE) ".replaceAll("LOCALE", locale) +
                     "FROM FormLoginErrorMessage fem " +
                     "WHERE fem.field=:field AND fem.reason=:reason";
        FormLoginErrorMessageDTO singleResult = entityManager.createQuery(HQL, FormLoginErrorMessageDTO.class)
                .setParameter("field", field)
                .setParameter("reason", reason).getSingleResult();
        return singleResult;
    }

    @Override
    public void save(FormLoginErrorMessage errorMessage) {
        entityManager.persist(errorMessage);
    }
}
