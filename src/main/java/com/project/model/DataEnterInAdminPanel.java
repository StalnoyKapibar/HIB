package com.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class DataEnterInAdminPanel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long dataEnterInOrders;
    private Long dataEnterInFeedback;

    public DataEnterInAdminPanel(Long dataEnterInOrders, Long dataEnterInFeedback) {
        this.dataEnterInOrders = dataEnterInOrders;
        this.dataEnterInFeedback = dataEnterInFeedback;
    }
}
