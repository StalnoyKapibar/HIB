package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookDTO20 {
    @EqualsAndHashCode.Include
    private long id;
    private String nameBookDTOLocale;
    private String nameTranslit;
    private String nameAuthorDTOLocale;
    private String authorTranslit;
    private Long price;
    private String coverImage;

    public BookDTO20(long id, String nameAuthorDTOLocale, String nameBookDTOLocale, Long price, String coverImage) {
        this.id = id;
        this.nameAuthorDTOLocale = nameAuthorDTOLocale;
        this.nameBookDTOLocale = nameBookDTOLocale;
        this.price = price;
        this.coverImage = coverImage;
    }
}
