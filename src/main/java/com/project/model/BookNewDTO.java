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
    private LocaleString name;
    private LocaleString author;
    private String coverImage;
    private List<Image> imageList;
}
