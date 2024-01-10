package com.project.shopapp.mappers;

import com.project.shopapp.dtos.requests.ProductRequestDto;
import com.project.shopapp.dtos.responses.ProductResponseDto;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ObjectMapperUtils objectMapperUtils;

    public List<ProductResponseDto> mapToDtoList(List<Product> products) {
        return products.stream().map(this::mapToDto).toList();
    }

    public Product mapToEntity(ProductRequestDto productRequestDto, Category category) {
        Product product = objectMapperUtils.mapToEntityOrDto(productRequestDto, Product.class);
        product.setCategory(category);
        return product;
    }

    public void mapToEntity(Product product, ProductRequestDto productRequestDto, Category category) {
        product.setCategory(category);
        objectMapperUtils.mapToPersistedEntity(product, productRequestDto);
    }

    public ProductResponseDto mapToDto(Product product) {
        ProductResponseDto productResponseDto = objectMapperUtils.mapToEntityOrDto(product, ProductResponseDto.class);
        productResponseDto.setCategoryId(product.getCategory().getId());
        return productResponseDto;
    }

}
