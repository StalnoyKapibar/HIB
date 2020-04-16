package com.project.service;

import com.project.dao.abstraction.FeedbackRequestDao;
import com.project.model.FeedbackRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackRequestServiceImpl implements FeedbackRequestService {
    private final FeedbackRequestDao feedbackRequestDAO;

    @Override
    public List<FeedbackRequest> findAll() {
        return feedbackRequestDAO.findAll();
    }

    @Override
    public List<FeedbackRequest> findAllByOrderByRepliedAsc() {
        return feedbackRequestDAO.findAllByOrderByRepliedAsc();
    }

    @Override
    public FeedbackRequest getById(Long id) {
        return feedbackRequestDAO.getById(id);
    }

    @Transactional
    @Override
    public FeedbackRequest save(FeedbackRequest feedbackRequest) {
        return feedbackRequestDAO.save(feedbackRequest);
    }

    @Override
    public List<FeedbackRequest> getByReplied(Boolean replied) {
        return feedbackRequestDAO.getByReplied(replied);
    }
}
