package com.project.service;

import com.project.model.FeedbackRequest;

import java.util.List;

public interface FeedbackRequestService {
    List<FeedbackRequest> findAll();

    List<FeedbackRequest> findAllByOrderByRepliedAsc();

    FeedbackRequest getById(Long id);

    FeedbackRequest save(FeedbackRequest feedbackRequest);

    List<FeedbackRequest> getByReplied(Boolean replied);
}
