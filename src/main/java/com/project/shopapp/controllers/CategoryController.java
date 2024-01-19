package com.project.shopapp.controllers;

import com.project.shopapp.dtos.requests.CategoryRequestDto;
import com.project.shopapp.dtos.responses.CategoryResponseDto;
import com.project.shopapp.dtos.responses.GenericResponse;
import com.project.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<CategoryResponseDto> responseDtoList = categoryService.getAllCategories();
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long categoryId) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long categoryId, @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryId, categoryRequestDto);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok(GenericResponse.empty());
    }

}
