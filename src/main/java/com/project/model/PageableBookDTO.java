package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageableBookDTO {
    private List<BookDTO> listBookDTO;
    private int TotalPages;
    private int numberPages;
    private int pageableSize;
}
