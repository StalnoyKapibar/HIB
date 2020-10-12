package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {
    private long id;
    private String name;
    private String path;
    private long parentId;
    private int viewOrder;
    private long booksCount;

    public static List<CategoryDTO> transformObj(List<Object[]> lst) {
        List<CategoryDTO> list = new ArrayList<>();
        for (Object[] obj : lst) {
            CategoryDTO cdto = new CategoryDTO();
            if (obj[0] != null) cdto.setId(((BigInteger) obj[0]).longValue());
            if (obj[1] != null) cdto.setName((String) obj[1]);
            if (obj[2] != null) cdto.setPath((String) obj[2]);
            if (obj[3] != null) cdto.setParentId(((BigInteger) obj[3]).longValue());
            if (obj[4] != null) cdto.setViewOrder((Integer) obj[4]);
            list.add(cdto);
        }
        return list;

    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", parentId=" + parentId +
                ", viewOrder=" + viewOrder +
                ", countBooks=" + booksCount +
                '}';
    }
}
