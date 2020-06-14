package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchPageDTO {
    private List<BookNewDTO> books;
    private Integer amountOfPages;
    private Integer numberPages;
    private Integer size;
    private Long amountOfBooksInDb;
    private boolean isShow;
}
