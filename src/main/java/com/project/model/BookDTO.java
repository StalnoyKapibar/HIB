package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private Integer price;
    private String coverImage;
    private List<Image> imageList;

}
