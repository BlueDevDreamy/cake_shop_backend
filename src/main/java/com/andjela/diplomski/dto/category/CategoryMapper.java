package com.andjela.diplomski.dto.category;

import com.andjela.diplomski.entity.codebook.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

    CategoryDto mapToCategoryDto(Category category);
    Category mapToCategory(CategoryDto categoryDto);
}
