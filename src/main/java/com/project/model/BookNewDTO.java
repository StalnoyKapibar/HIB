package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
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

    public BookNewDTO(long id, LocaleString name, LocaleString author, String coverImage, Collection<Image> imageList) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.coverImage = coverImage;
        this.imageList = (List<Image>) imageList;
    }

}
