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
    private String nameAuthorDTOLocale;
    private String nameBookDTOLocale;
    private String coverImage;
}
