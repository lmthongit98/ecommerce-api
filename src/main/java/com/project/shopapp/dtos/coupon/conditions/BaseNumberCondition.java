package com.project.shopapp.dtos.coupon.conditions;

import com.project.shopapp.enums.Attribute;
import com.project.shopapp.enums.AttributeType;

public abstract class BaseNumberCondition extends BaseCondition {

    protected final Double requiredValue;
    protected final Double attributeValue;

    public BaseNumberCondition(Attribute attribute, String value, Object attributeValue) {
        super(attribute);
        if (!attribute.getType().isValid(value, attributeValue)) {
            throw new IllegalArgumentException("Value is not valid");
        }
        this.requiredValue = Double.valueOf(value);
        this.attributeValue = (Double) attributeValue;
    }

    @Override
    public AttributeType getRequiredAttributeType() {
        return AttributeType.NUMBER;
    }


}
