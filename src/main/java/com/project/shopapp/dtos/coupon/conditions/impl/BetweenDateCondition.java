package com.project.shopapp.dtos.coupon.conditions.impl;

import com.project.shopapp.dtos.coupon.conditions.BaseDateCondition;
import com.project.shopapp.enums.Attribute;

public class BetweenDateCondition extends BaseDateCondition {

    public BetweenDateCondition(Attribute attribute, String value, Object attributeValue) {
        super(attribute, value, attributeValue);
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
