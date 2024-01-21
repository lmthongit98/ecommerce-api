package com.project.shopapp.dtos.coupon.conditions;

import com.project.shopapp.enums.AttributeType;

import java.util.Map;

public abstract class BaseNumberCondition extends BaseCondition {

    protected final Double requiredValue;
    protected final Double attributeValue;

    public BaseNumberCondition(String attribute, AttributeType attributeType, String value, Map<String, Object> attributeValueMap) {
        super(attribute, attributeType);
        Object actualValue = attributeValueMap.get(attribute);
        if (!AttributeType.NUMBER.equals(attributeType) || !attributeType.isValid(value, actualValue)) {
            throw new IllegalArgumentException("Type mismatch");
        }
        this.requiredValue = Double.valueOf(value);
        this.attributeValue = (Double) actualValue;
    }

}
