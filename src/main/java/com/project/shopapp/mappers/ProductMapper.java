package com.project.shopapp.mappers;

import com.project.shopapp.dtos.requests.ProductRequestDto;
import com.project.shopapp.dtos.responses.ProductImageDto;
import com.project.shopapp.dtos.responses.ProductResponseDto;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.services.FileService;
import com.project.shopapp.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        if (StringUtils.hasText(product.getThumbnail())) {
            productResponseDto.setThumbnail(FileService.getImageUrl(product.getThumbnail()));
        }
        List<ProductImageDto> productImages = product.getProductImages().stream().map
                (productImage -> new ProductImageDto(productImage.getId(), FileService.getImageUrl(productImage.getImageName()))).toList();
        productResponseDto.setProductImages(productImages);
        return productResponseDto;
    }

}
