package com.project.shopapp.dtos.coupon;

import com.project.shopapp.dtos.coupon.conditions.GenericCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.BetweenDateCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.GreaterThanCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.GreaterThanOrEqualCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.InCondition;
import com.project.shopapp.models.CouponCondition;

public class CouponConditionFactory {

    public static GenericCondition createCouponCondition(CouponCondition couponCondition, Object attributeValue) {
        return switch (couponCondition.getOperator()) {
            case GREATER_THAN ->
                    new GreaterThanCondition(couponCondition.getAttribute(), couponCondition.getValue(), attributeValue);
            case GREATER_THAN_OR_EQUAL ->
                    new GreaterThanOrEqualCondition(couponCondition.getAttribute(), couponCondition.getValue(), attributeValue);
            case BETWEEN ->
                    new BetweenDateCondition(couponCondition.getAttribute(), couponCondition.getValue(), attributeValue);
            case IN -> new InCondition(couponCondition.getAttribute(), couponCondition.getValue(), attributeValue);
            default -> throw new IllegalStateException("Unexpected value: " + couponCondition.getOperator());
        };
    }
}
