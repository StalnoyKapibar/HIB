package com.project.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookNewDTO {
    @EqualsAndHashCode.Include
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
    @Singular("imageList")
    private List<Image> imageList;
}
