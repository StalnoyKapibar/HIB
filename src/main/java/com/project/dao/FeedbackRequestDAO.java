package com.project.dao;

import com.project.model.FeedbackRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRequestDAO extends JpaRepository<FeedbackRequest, Long> {
    List<FeedbackRequest> findAllByOrderByRepliedAsc();
}
