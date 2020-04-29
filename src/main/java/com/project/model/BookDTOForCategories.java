package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTOForCategories {
    private long id;
    private String name;
    private String author;
    private String edition;
    private String yearOfEdition;
    private Long price;
    private Long pages;
    private String coverImage;
    private Category category;
}
