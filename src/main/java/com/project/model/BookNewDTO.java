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
    private String nameTranslit;
    private String author;
    private String authorTranslit;
    private String desc;
    private String edition;
    private String editionTranslit;
    private String yearOfEdition;
    private Long pages;
    private Long price;
    private String originalLanguage;
    private String coverImage;
    private List<Image> imageList;
    private Category category;
    private boolean isShow;

    public BookNewDTO(long id, String name, String author, String desc, String edition,
                      String yearOfEdition, Long pages, Long price, String originalLanguage, String coverImage, boolean isShow) {
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
        this.isShow = isShow;
    }

    public BookNewDTO(long id, String name, String nameTranslit, String author, String authorTranslit,
                      String desc, String edition, String editionTranslit, String yearOfEdition, Long pages,
                      Long price, String originalLanguage, String coverImage, boolean isShow) {
        this.id = id;
        this.name = name;
        this.nameTranslit = nameTranslit;
        this.author = author;
        this.authorTranslit = authorTranslit;
        this.desc = desc;
        this.edition = edition;
        this.editionTranslit = editionTranslit;
        this.yearOfEdition = yearOfEdition;
        this.pages = pages;
        this.price = price;
        this.originalLanguage = originalLanguage;
        this.coverImage = coverImage;
        this.isShow = isShow;
    }

    public BookNewDTO(long id, String name, String nameTranslit, String author, String authorTranslit,
                      boolean isShow) {
        this.id = id;
        this.name = name;
        this.nameTranslit = nameTranslit;
        this.author = author;
        this.authorTranslit = authorTranslit;
        this.isShow = isShow;
    }

    public BookNewDTO(long id, String name, String nameTranslit, String author, String authorTranslit,
                      String desc, String edition, String editionTranslit, String yearOfEdition, Long pages,
                      Long price, String originalLanguage, String coverImage, Category category, boolean isShow) {
        this.id = id;
        this.name = name;
        this.nameTranslit = nameTranslit;
        this.author = author;
        this.authorTranslit = authorTranslit;
        this.desc = desc;
        this.edition = edition;
        this.editionTranslit = editionTranslit;
        this.yearOfEdition = yearOfEdition;
        this.pages = pages;
        this.price = price;
        this.originalLanguage = originalLanguage;
        this.coverImage = coverImage;
        this.category = category;
        this.isShow = isShow;
    }

    public BookNewDTO(long id) {
        this.id = id;
    }

    public BookNewDTO(long id, String name, String nameTranslit, String author, String authorTranslit) {
        this.id = id;
        this.name = name;
        this.nameTranslit = nameTranslit;
        this.author = author;
        this.authorTranslit = authorTranslit;
    }
}
