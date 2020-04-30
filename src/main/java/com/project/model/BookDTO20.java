package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookDTO20 {
    @EqualsAndHashCode.Include
    private long id;
    private String nameAuthorDTOLocale;
    private String nameAuthorTransliteDTOLocale;
    private String nameBookTransliteDTOLocale;
    private String nameBookDTOLocale;
    private Long price;
    private String coverImage;
    private OtherLanguage otherLanguage;
    private String originalLanguage;

    public BookDTO20(long id, String nameAuthorDTOLocale,
                     String nameBookDTOLocale, Long price,
                     String coverImage, OtherLanguage otherLanguage,
                     String originalLanguage) {
        this.id = id;
        this.nameAuthorDTOLocale = nameAuthorDTOLocale;
        this.nameBookDTOLocale = nameBookDTOLocale;
        this.price = price;
        this.coverImage = coverImage;
        this.originalLanguage = originalLanguage;
    }
}
