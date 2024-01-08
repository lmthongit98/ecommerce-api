package com.project.shopapp.repositories;

import com.project.shopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "WHERE (p.name LIKE CONCAT('%', :searchKey, '%') OR p.description LIKE CONCAT('%', :searchKey, '%')) " +
            "AND p.active = true AND p.category.id = :categoryId ")
    Page<Product> searchProducts(Long categoryId, String searchKey, Pageable pageable);
}
