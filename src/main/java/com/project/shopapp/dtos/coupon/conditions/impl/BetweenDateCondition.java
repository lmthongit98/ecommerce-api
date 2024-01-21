package com.project.shopapp.dtos.coupon.conditions.impl;

import com.project.shopapp.enums.AttributeType;
import com.project.shopapp.dtos.coupon.conditions.BaseDateCondition;

import java.util.Map;

public class BetweenDateCondition extends BaseDateCondition {

    public BetweenDateCondition(String attribute, AttributeType attributeType, String value, Map<String, Object> attributeValueMap) {
        super(attribute, attributeType, value, attributeValueMap);
    }

    @Override
    public boolean isConditionMeet() {
        if (attributeValue.isEqual(fromDate) || attributeValue.isEqual(toDate)) {
            return true;
        }
        return attributeValue.isAfter(fromDate) && attributeValue.isBefore(toDate);
    }

    @Override
    public String getInvalidMessage() {
        return String.format("%s must between %s and %s", attribute, fromDate.toString(), toDate.toString());
    }

}
