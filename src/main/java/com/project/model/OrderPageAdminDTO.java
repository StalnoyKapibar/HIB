package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPageAdminDTO {
    private List<OrderDTO> listOrderDTO;
    private int totalPages;
    private int pageNumber;
    private int pageableSize;
}