package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.CategoryRequestDto;
import com.project.shopapp.dtos.responses.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto getCategoryById(Long categoryId);

    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto);

    void deleteCategoryById(Long categoryId);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
}
