package com.project.dao.abstraction;

import com.project.model.FeedbackRequest;

import java.util.List;

public interface FeedbackRequestDao extends IGenericDao<Long, FeedbackRequest>{

    List<FeedbackRequest> findAllByOrderByRepliedAsc();

    FeedbackRequest save(FeedbackRequest feedbackRequest);

    List<FeedbackRequest> getByReplied(Boolean replied);
}
