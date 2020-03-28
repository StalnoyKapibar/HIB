package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItem {
    @EqualsAndHashCode.Include
    private BookDTO book;
    private Integer quantity;
}
