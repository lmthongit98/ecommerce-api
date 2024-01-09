package com.project.shopapp.mappers;

import com.project.shopapp.utils.ObjectMapperUtils;
import com.project.shopapp.dtos.responses.ProductResponseDto;
import com.project.shopapp.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ObjectMapperUtils objectMapperUtils;

    public List<ProductResponseDto> mapToDtoList(List<Product> products) {
        return products.stream().map(product -> {
            ProductResponseDto productResponseDto = objectMapperUtils.convertEntityAndDto(product, ProductResponseDto.class);
            productResponseDto.setCategoryId(product.getCategory().getId());
            return productResponseDto;
        }).toList();
    }

}
