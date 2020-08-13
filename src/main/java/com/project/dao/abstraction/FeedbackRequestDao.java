package com.project.dao.abstraction;

import com.project.model.FeedbackRequest;

import java.util.List;

public interface FeedbackRequestDao extends GenericDao<Long, FeedbackRequest> {

    List<FeedbackRequest> findAllByOrderByRepliedAsc();

    List<FeedbackRequest> getByReplied(Boolean replied);

    List<FeedbackRequest> getByViewed(Boolean viewed);

    List<FeedbackRequest> getBySenderByReplied(String senderEmail, boolean replied);

    int getCountOfFeedBack();

    Long getAmountByReplied(boolean replied, String senderEmail);

    void deleteFeedbackRequestByIbBook(Long bookId);

    List<FeedbackRequest> findAllRequestByIdBook(Long bookId);

    List<FeedbackRequest> findAllUnreadRequestsByBookId(Long bookId);
}
