package com.project.shopapp.models;

import com.project.shopapp.enums.AttributeType;
import com.project.shopapp.enums.Operator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coupon_conditions")
@Getter
@Setter
public class CouponCondition extends BaseEntity {

    @Column(name = "attribute", unique = true, nullable = false)
    private String attribute;

    @Column(name = "attribute_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttributeType attributeType;

    @Column(name = "operator")
    @Enumerated(EnumType.STRING)
    private Operator operator;

    @Column(name = "value")
    private String value;

    @Column(name = "condtion_group")
    private Integer conditionGroup; // OR (different group) - AND (same group)

    @JoinColumn(name = "coupon_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;
}
