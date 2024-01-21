package com.project.shopapp.dtos.coupon;

import com.project.shopapp.dtos.coupon.conditions.GenericCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.BetweenDateCondition;
import com.project.shopapp.models.CouponCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.GreaterThanCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.GreaterThanOrEqualCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.InCondition;

import java.util.Map;

public class CouponConditionFactory {

    public static GenericCondition createCouponCondition(CouponCondition couponCondition, Map<String, Object> attributeMap) {
        return switch (couponCondition.getOperator()) {
            case GREATER_THAN ->
                    new GreaterThanCondition(couponCondition.getAttribute(), couponCondition.getAttributeType(), couponCondition.getValue(), attributeMap);
            case GREATER_THAN_OR_EQUAL ->
                    new GreaterThanOrEqualCondition(couponCondition.getAttribute(), couponCondition.getAttributeType(), couponCondition.getValue(), attributeMap);
            case BETWEEN ->
                    new BetweenDateCondition(couponCondition.getAttribute(), couponCondition.getAttributeType(), couponCondition.getValue(), attributeMap);
            case IN ->
                    new InCondition(couponCondition.getAttribute(), couponCondition.getAttributeType(), couponCondition.getValue(), attributeMap);
            default -> throw new IllegalStateException("Unexpected value: " + couponCondition.getOperator());
        };
    }
}
