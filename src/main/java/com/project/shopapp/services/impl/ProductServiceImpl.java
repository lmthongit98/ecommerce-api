package com.project.shopapp.services.impl;

import com.project.shopapp.mappers.ProductMapper;
import com.project.shopapp.utils.PageableUtils;
import com.project.shopapp.dtos.requests.ProductRequestDto;
import com.project.shopapp.dtos.responses.ProductResponseDto;
import com.project.shopapp.dtos.responses.ResponseDto;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ResponseDto<ProductResponseDto> getProducts(Long categoryId, String searchKey, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy, sortDir);
        Page<Product> products = productRepository.searchProducts(categoryId, searchKey, pageable);
        List<Product> listOfItems = products.getContent();
        List<ProductResponseDto> content = productMapper.mapToDtoList(listOfItems);
        return new ResponseDto<>(products, content);
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        return null;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public void deleteProductById(Long productId) {

    }

}
