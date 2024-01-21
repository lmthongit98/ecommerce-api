package com.project.shopapp.dtos.coupon.conditions.impl;

import com.project.shopapp.enums.AttributeType;
import com.project.shopapp.dtos.coupon.conditions.BaseNumberCondition;

import java.util.Map;

public class GreaterThanOrEqualCondition extends BaseNumberCondition {

    public GreaterThanOrEqualCondition(String attribute, AttributeType attributeType, String value, Map<String, Object> attributeValueMap) {
        super(attribute, attributeType, value, attributeValueMap);
    }

    @Override
    public boolean isConditionMeet() {
        return attributeValue >= requiredValue;
    }

    @Override
    public String getInvalidMessage() {
        return String.format("%s must be >= %.2f", attribute, requiredValue);
    }

}
