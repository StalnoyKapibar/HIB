package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageableBookDto {
    private List<BookDto> listBookDto;
    private int TotalPages;
    private int numberPages;
    private int pageableSize;
}
