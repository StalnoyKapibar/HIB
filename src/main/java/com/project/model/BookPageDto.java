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
public class BookPageDto {
    private List<BookDTO> books;
    private Integer amountOfPages;
    private Integer numberPages;
    private Integer size;
    private Long amountOfBooksInDb;
}
