package com.project.dao;

import com.project.model.FeedbackRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@AllArgsConstructor
public class FeedbackRequestDAOImpl implements FeedbackRequestDAO {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<FeedbackRequest> findAllByOrderByRepliedAsc() {
        return entityManager
                .createQuery("FROM FeedbackRequest f ORDER BY f.replied ASC", FeedbackRequest.class)
                .getResultList();
    }

    @Override
    public FeedbackRequest getById(Long id) {
        return entityManager.find(FeedbackRequest.class, id);
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
}
