package com.project.shopapp.dtos.coupon.conditions;

import com.project.shopapp.enums.Attribute;
import com.project.shopapp.enums.AttributeType;
import lombok.Getter;

@Getter
public abstract class BaseCondition implements GenericCondition {

    protected final Attribute attribute;

    public BaseCondition(Attribute attribute) {
        if (!this.getRequiredAttributeType().equals(attribute.getType())) {
            throw new IllegalArgumentException(String.format("Type mismatch: required type of %s", getRequiredAttributeType().name()));
        }
        this.attribute = attribute;
    }

    public abstract AttributeType getRequiredAttributeType();
}
