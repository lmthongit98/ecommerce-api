package com.project.shopapp.models;

import jakarta.persistence.*;
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

    @Column(name = "active", nullable = false)
    private Boolean active = Boolean.TRUE;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private Set<CouponCondition> couponConditions = new HashSet<>();

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

}
