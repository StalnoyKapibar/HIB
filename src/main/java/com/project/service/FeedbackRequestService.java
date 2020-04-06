package com.project.service;

import com.project.model.FeedbackRequest;

import java.util.List;

public interface FeedbackRequestService {
    List<FeedbackRequest> findAll();

    FeedbackRequest getById(Long id);

    FeedbackRequest save(FeedbackRequest feedbackRequest);
}
