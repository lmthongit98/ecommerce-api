package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.requests.ProductRequestDto;
import com.project.shopapp.dtos.responses.ProductResponseDto;
import com.project.shopapp.dtos.responses.PagingResponseDto;
import com.project.shopapp.exceptions.ResourceNotFoundException;
import com.project.shopapp.mappers.ProductMapper;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.services.ProductService;
import com.project.shopapp.utils.PageableUtils;
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
    public PagingResponseDto<ProductResponseDto> getProducts(Long categoryId, String searchKey, int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageableUtils.getPageable(pageNo, pageSize, sortBy, sortDir);
        Page<Product> products = productRepository.searchProducts(categoryId, searchKey, pageable);
        List<Product> listOfItems = products.getContent();
        List<ProductResponseDto> content = productMapper.mapToDtoList(listOfItems);
        return new PagingResponseDto<>(products, content);
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        Product product = findProductById(productId);
        return productMapper.mapToDto(product);
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Category category = findCategoryById(productRequestDto.getCategoryId());
        Product product = productMapper.mapToEntity(productRequestDto, category);
        Product savedProduct = productRepository.save(product);
        return productMapper.mapToDto(savedProduct);
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto) {
        Product product = findProductById(productId);
        Category category = findCategoryById(productRequestDto.getCategoryId());
        productMapper.mapToEntity(product, productRequestDto, category);
        Product savedProduct = productRepository.save(product);
        return productMapper.mapToDto(savedProduct);
    }

    @Override
    public void deleteProductById(Long productId) {
        Product product = findProductById(productId);
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public boolean existsByName(String productName) {
        return productRepository.existsByName(productName);
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", productId));
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));
    }


}
