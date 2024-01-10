package com.project.shopapp.services.impl;

import com.project.shopapp.exceptions.ResourceNotFoundException;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.utils.ObjectMapperUtils;
import com.project.shopapp.dtos.requests.CategoryRequestDto;
import com.project.shopapp.dtos.responses.CategoryResponseDto;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ObjectMapperUtils objectMapperUtils;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = objectMapperUtils.mapToEntityOrDto(categoryRequestDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return objectMapperUtils.mapToEntityOrDto(savedCategory, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
        Category category = getCategory(categoryId);
        return objectMapperUtils.mapToEntityOrDto(category, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
        Category category = getCategory(categoryId);
        category.setName(categoryRequestDto.getName());
        return objectMapperUtils.mapToEntityOrDto(category, CategoryResponseDto.class);
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        Category category = getCategory(categoryId);
        List<Product> products = productRepository.findByCategory(category);
        if (!products.isEmpty()) {
            throw new IllegalStateException("Cannot delete category with associated products");
        }
        categoryRepository.delete(category);

    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return objectMapperUtils.mapAll(categories, CategoryResponseDto.class);
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));
    }

}
