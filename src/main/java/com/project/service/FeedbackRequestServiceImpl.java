package com.project.service;

import com.project.dao.FeedbackRequestDAO;
import com.project.model.FeedbackRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackRequestServiceImpl implements FeedbackRequestService {
    private final FeedbackRequestDAO feedbackRequestDAO;

    public FeedbackRequestServiceImpl(FeedbackRequestDAO feedbackRequestDAO) {
        this.feedbackRequestDAO = feedbackRequestDAO;
    }

    @Override
    public List<FeedbackRequest> findAll() {
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
}
