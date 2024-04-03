package com.andjela.diplomski.dto.category;

import com.andjela.diplomski.entity.codebook.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String name;
    private Category parentCategory;
    private int level;
}
