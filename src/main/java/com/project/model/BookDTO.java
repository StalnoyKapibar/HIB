package com.project.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookDTO {
    @EqualsAndHashCode.Include
    private long id;
    private LocaleString name;
    private LocaleString author;
    private LocaleString desc;
    private LocaleString edition;
    private String yearOfEdition;
    private Long pages;
    private Long price;
    private String originalLanguage;
    private String coverImage;
    @Singular("imageList")
    private List<Image> imageList;
    private boolean isShow;
    private Category category;

    public BookDTO(long id, LocaleString name, LocaleString author, LocaleString desc, LocaleString edition, String yearOfEdition, Long pages, Long price, String originalLanguage, String coverImage, List<Image> imageList, boolean isShow) {
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
        this.imageList = imageList;
        this.isShow = isShow;
    }
}
