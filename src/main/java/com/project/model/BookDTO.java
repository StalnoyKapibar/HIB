package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private long id;
    private LocaleString name;
    private LocaleString author;
    private String coverImage;
}
