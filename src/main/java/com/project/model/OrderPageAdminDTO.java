package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPageAdminDTO {
    private List<Order> listBookDTO;
    private int TotalPages;
    private int numberPages;
    private int pageableSize;
    private boolean isShow;
}