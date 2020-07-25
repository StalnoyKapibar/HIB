package com.project.service;

import com.project.dao.abstraction.FeedbackRequestDao;
import com.project.model.FeedbackRequest;
import com.project.service.abstraction.FeedbackRequestService;
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
        return feedbackRequestDAO.findById(id);
    }

    @Transactional
    @Override
    public FeedbackRequest save(FeedbackRequest feedbackRequest) {
        if (feedbackRequest.getId() == null) {
            feedbackRequestDAO.add(feedbackRequest);
        } else {
            feedbackRequestDAO.update(feedbackRequest);
        }
        return feedbackRequest;
    }

    @Override
    public List<FeedbackRequest> getByReplied(Boolean replied) {
        return feedbackRequestDAO.getByReplied(replied);
    }

    @Override
    public List<FeedbackRequest> getBySenderByReplied(String senderEmail, boolean replied) {
        return feedbackRequestDAO.getBySenderByReplied(senderEmail, replied);
    }

    @Override
    public int getCountOfFeedBack() {
        return feedbackRequestDAO.getCountOfFeedBack();
    }

    @Override
    public Long[] getAmountOfFeedback(String senderEmail) {
        Long unrepliedFeedbacks = feedbackRequestDAO.getAmountByReplied(false, senderEmail);
        Long repliedFeedbacks = feedbackRequestDAO.getAmountByReplied(true, senderEmail);
        return new Long[] {unrepliedFeedbacks, repliedFeedbacks};
    }

    @Override
    public void deleteFeedbackRequestByIbBook(Long bookId){
        feedbackRequestDAO.deleteFeedbackRequestByIbBook(bookId);
    }

    @Override
    public List<FeedbackRequest> findAllRequestByIdBook(Long bookId) {
        return feedbackRequestDAO.findAllRequestByIdBook(bookId);
    }
}
