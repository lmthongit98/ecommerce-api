package com.project.shopapp.repositories;

import com.project.shopapp.models.Coupon;
import com.project.shopapp.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("SELECT c FROM Coupon c LEFT JOIN FETCH c.couponConditions WHERE c.code = :code and c.active = :active")
    Optional<Coupon> findByCodeAndActive(String code, boolean active);

    @Query("SELECT c FROM Coupon c LEFT JOIN FETCH c.couponConditions WHERE c.code LIKE CONCAT('%', :searchKey, '%')")
    Page<Coupon> findByKeyword(String searchKey, Pageable pageable);
}
