package com.project.shopapp.dtos.coupon.conditions.impl;

import com.project.shopapp.dtos.coupon.conditions.BaseCondition;
import com.project.shopapp.enums.Attribute;
import com.project.shopapp.enums.AttributeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
    public boolean isConditionMeet() {
        Set<String> requiredValuesSet = new HashSet<>(Arrays.asList(requiredValues));
        return Arrays.stream(attributeValues).anyMatch(requiredValuesSet::contains);
    }

    @Override
    public String getInvalidMessage() {
        return String.format("%s must be one of %s", attribute, Arrays.toString(requiredValues));
    }

    @Override
    public AttributeType getRequiredAttributeType() {
        return AttributeType.STRING;
    }

}
