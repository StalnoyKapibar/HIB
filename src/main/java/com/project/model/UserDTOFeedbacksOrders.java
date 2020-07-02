package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTOFeedbacksOrders {
    private String email;
    private Long unrepliedFeedbacks;
    private Long repliedFeedbacks;
    private Long uprocessedOrders;
    private Long processingOrders;
    private Long completedOrders;
    private Long deletedOrders;
}
