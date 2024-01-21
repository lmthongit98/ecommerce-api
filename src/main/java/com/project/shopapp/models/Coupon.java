package com.project.shopapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coupons")
@Getter
@Setter
public class Coupon extends BaseEntity {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "discount_percentage", nullable = false)
    private Integer discountPercentage;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @OneToMany(mappedBy = "coupon")
    private Set<CouponCondition> couponConditions = new HashSet<>();

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

}
