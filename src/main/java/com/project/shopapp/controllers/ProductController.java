package com.project.shopapp.controllers;

import com.github.javafaker.Faker;
import com.project.shopapp.constants.AppConstants;
import com.project.shopapp.dtos.requests.ProductRequestDto;
import com.project.shopapp.dtos.responses.ProductResponseDto;
import com.project.shopapp.dtos.responses.PagingResponseDto;
import com.project.shopapp.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(value = "category_id") Long categoryId,
            @RequestParam(value = "search_key", defaultValue = AppConstants.EMPTY, required = false) String searchKey,
            @RequestParam(value = "page_no", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "page_size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sort_by", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sort_dir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        PagingResponseDto<ProductResponseDto> pagingResponseDto = productService.getProducts(categoryId, searchKey, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(pagingResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) {
        ProductResponseDto productResponseDto = productService.getProductById(productId);
        return ResponseEntity.ok(productResponseDto);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        ProductResponseDto productResponseDto = productService.createProduct(productRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long productId, @ModelAttribute("files") List<MultipartFile> files) {
        var productImages = productService.uploadProductImages(productId, files);
        return ResponseEntity.ok(productImages);
    }

    @GetMapping("/images/{imageName:.+}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) throws MalformedURLException {
        Resource resource = productService.getImage(imageName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable("id") Long productId, @Valid @RequestBody ProductRequestDto productRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        ProductResponseDto productResponseDto = productService.updateProduct(productId, productRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItemById(@PathVariable("id") Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", productId));
    }

    @PostMapping("/generateFakeProducts")
    private ResponseEntity<String> generateFakeProducts() {
        Faker faker = new Faker();
        for (int i = 0; i < 1_000; i++) {
            String productName = faker.commerce().productName();
            if (productService.existsByName(productName)) {
                continue;
            }
            ProductRequestDto productDTO = ProductRequestDto.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(10, 90_000_000))
                    .description(faker.lorem().sentence())
                    .categoryId((long) faker.number().numberBetween(1, 4))
                    .build();
            try {
                productService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Fake Products created successfully");
    }

}
