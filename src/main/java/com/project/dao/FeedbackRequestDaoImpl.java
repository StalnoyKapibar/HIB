package com.project.dao;

import com.project.dao.abstraction.FeedbackRequestDao;
import com.project.model.FeedbackRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FeedbackRequestDaoImpl extends AbstractDao<Long, FeedbackRequest> implements FeedbackRequestDao {

    FeedbackRequestDaoImpl(){super(FeedbackRequest.class);}

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FeedbackRequest> findAllByOrderByRepliedAsc() {
        return entityManager
                .createQuery("FROM FeedbackRequest f ORDER BY f.replied ASC", FeedbackRequest.class)
                .getResultList();
    }

    @Override
    public FeedbackRequest save(FeedbackRequest feedbackRequest) {
        if (feedbackRequest.getId() == null) {
            entityManager.persist(feedbackRequest);
        } else {
            entityManager.merge(feedbackRequest);
        }
        return feedbackRequest;
    }

    @Override
    public List<FeedbackRequest> getByReplied(Boolean replied) {
        return entityManager
                .createQuery("FROM FeedbackRequest f WHERE replied = :replied", FeedbackRequest.class)
                .setParameter("replied", replied).getResultList();
    }
}
