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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocaleString name;

    private Long parentId;

    private int viewOrder;

    public Category(Long id, LocaleString name, Long parentId, int viewOrder) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.viewOrder = viewOrder;
    }

    public Category(Long id) {
        this.id = id;
    }

    public Category(LocaleString name) {
        this.name = name;
    }

    public Category(LocaleString name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
