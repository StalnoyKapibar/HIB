package com.project.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private Long parentId;
    private int viewOrder;

    public Category(Long id, String categoryName, Long parentId, int viewOrder) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.viewOrder = viewOrder;
    }

    public Category(Long id) {
        this.id = id;
    }

    public Category(String categoryName, Long parentId) {
        this.categoryName = categoryName;
        this.parentId = parentId;
    }
}
