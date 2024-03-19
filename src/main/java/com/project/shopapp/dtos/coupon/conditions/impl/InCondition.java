package com.project.shopapp.dtos.coupon.conditions.impl;

import com.project.shopapp.dtos.coupon.conditions.BaseCondition;
import com.project.shopapp.dtos.coupon.visitors.ConditionVisitor;
import com.project.shopapp.enums.Attribute;
import com.project.shopapp.enums.AttributeType;
import lombok.Getter;

@Getter
public class InCondition extends BaseCondition {

    private final String[] requiredValues;
    private final String[] attributeValues;

    public InCondition(Attribute attribute, String value, Object attributeValue) {
        super(attribute);
        if (!attribute.getType().isValid(value, attributeValue)) {
            throw new IllegalArgumentException("Value is not valid");
        }
        this.requiredValues = value.split(";");
        this.attributeValues = (String[]) attributeValue;
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public AttributeType getRequiredAttributeType() {
        return AttributeType.STRING;
    }

}
