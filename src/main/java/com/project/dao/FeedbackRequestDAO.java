package com.project.dao;

import com.project.model.FeedbackRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRequestDAO extends JpaRepository<FeedbackRequest, Long> {
}
