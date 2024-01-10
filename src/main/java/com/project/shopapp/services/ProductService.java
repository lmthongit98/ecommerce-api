package com.project.shopapp.services;

import com.project.shopapp.dtos.requests.ProductRequestDto;
import com.project.shopapp.dtos.responses.ProductResponseDto;
import com.project.shopapp.dtos.responses.PagingResponseDto;

public interface ProductService {
    PagingResponseDto<ProductResponseDto> getProducts(Long categoryId, String searchKey, int pageNo, int pageSize, String sortBy, String sortDir);

    ProductResponseDto getProductById(Long productId);

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto);

    void deleteProductById(Long productId);
}
