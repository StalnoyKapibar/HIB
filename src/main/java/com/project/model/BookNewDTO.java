package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookNewDTO {
    private long id;
    private String name;
    private String author;
    private String desc;
    private String edition;
    private String yearOfEdition;
    private Long pages;
    private Long price;
    private String originalLanguage;
    private String coverImage;
    private List<Image> imageList;

    public BookNewDTO(long id, String name, String author, String desc, String edition, String yearOfEdition, Long pages, Long price, String originalLanguage, String coverImage) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.desc = desc;
        this.edition = edition;
        this.yearOfEdition = yearOfEdition;
        this.pages = pages;
        this.price = price;
        this.originalLanguage = originalLanguage;
        this.coverImage = coverImage;
    }
}
