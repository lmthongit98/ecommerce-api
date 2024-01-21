package com.project.shopapp.dtos.coupon.conditions;

import com.project.shopapp.enums.AttributeType;

public abstract class BaseCondition implements GenericCondition {
    protected final String attribute;
    protected final AttributeType attributeType;

    public BaseCondition(String attribute, AttributeType attributeType) {
        this.attribute = attribute;
        this.attributeType = attributeType;
    }
}
