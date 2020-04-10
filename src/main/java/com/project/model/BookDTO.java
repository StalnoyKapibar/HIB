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
    private Boolean statusInStock;

}
