package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItemDTO {

    private Long id;

    @EqualsAndHashCode.Include
    private Book book;

    public CartItemDTO(Book book) {
        this.book = book;
    }
}
