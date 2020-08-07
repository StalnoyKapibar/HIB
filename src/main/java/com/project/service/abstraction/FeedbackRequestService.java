package com.project.service.abstraction;

import com.project.model.FeedbackRequest;

import java.util.List;

public interface FeedbackRequestService {
    List<FeedbackRequest> findAll();

    List<FeedbackRequest> findAllByOrderByRepliedAsc();

    FeedbackRequest getById(Long id);

    FeedbackRequest save(FeedbackRequest feedbackRequest);

    List<FeedbackRequest> getByReplied(Boolean replied);

    List<FeedbackRequest> getBySenderByReplied(String senderEmail, boolean replied);

    int getCountOfFeedBack();

    Long[] getAmountOfFeedback(String senderEmail);

    void deleteFeedbackRequestByIbBook(Long bookId);

    List<FeedbackRequest> findAllRequestByIdBook(Long bookId);

    List<FeedbackRequest> findAllUnreadRequestsByBookId(Long bookId);
}
