package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItemDto {

    private Long id;

    @EqualsAndHashCode.Include
    private BookDto book;

    private Integer quantity;

    public CartItemDto(BookDto book, Integer quantity) {
        this.book = book;
        this.quantity = quantity;
    }
}
