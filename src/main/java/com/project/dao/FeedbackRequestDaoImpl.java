package com.project.dao;

import com.project.dao.abstraction.FeedbackRequestDao;
import com.project.model.FeedbackRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackRequestDaoImpl extends AbstractDao<Long, FeedbackRequest> implements FeedbackRequestDao {

    FeedbackRequestDaoImpl(){super(FeedbackRequest.class);}

    @Override
    public List<FeedbackRequest> findAllByOrderByRepliedAsc() {
        return entityManager
                .createQuery("FROM FeedbackRequest f ORDER BY f.replied ASC", FeedbackRequest.class)
                .getResultList();
    }

    @Override
    public List<FeedbackRequest> getByReplied(Boolean replied) {
        return entityManager
                .createQuery("FROM FeedbackRequest f WHERE replied = :replied", FeedbackRequest.class)
                .setParameter("replied", replied).getResultList();
    }

    @Override
    public int getCountOfFeedBack(long lastAuthDate) {
        return entityManager
                .createQuery("FROM FeedbackRequest  where data>=:data", FeedbackRequest.class)
                .setParameter("data", lastAuthDate)
                .getResultList()
                .size();
    }

    @Override
    public Long getAmountByReplied(boolean replied, String senderEmail) {
        return (Long) entityManager
                .createQuery("SELECT count(*) FROM FeedbackRequest f WHERE replied = :replied AND senderEmail = :senderEmail")
                .setParameter("replied", replied)
                .setParameter("senderEmail", senderEmail)
                .getSingleResult();
    }
}
