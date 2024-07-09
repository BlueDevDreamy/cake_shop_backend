package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.category.CategoryDto;
import com.andjela.diplomski.dto.category.CategoryMapper;
import com.andjela.diplomski.entity.codebook.Category;
import com.andjela.diplomski.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> foundCategories = new ArrayList<>();

        for (Category category : categories) {
            CategoryDto categoryDto = CategoryMapper.MAPPER.mapToCategoryDto(category);
            foundCategories.add(categoryDto);
        }
        return foundCategories;
    }
}
