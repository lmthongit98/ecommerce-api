package com.project.shopapp.models;

import com.project.shopapp.enums.Attribute;
import com.project.shopapp.enums.Operator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coupon_conditions")
@Getter
@Setter
public class CouponCondition extends BaseEntity {

    @Column(name = "attribute", nullable = false)
    @Enumerated(EnumType.STRING)
    private Attribute attribute;

    @Column(name = "operator")
    @Enumerated(EnumType.STRING)
    private Operator operator;

    @Column(name = "value")
    private String value;

    @JoinColumn(name = "coupon_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;
}
