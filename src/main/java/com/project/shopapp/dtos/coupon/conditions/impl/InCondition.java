package com.project.shopapp.dtos.coupon.conditions.impl;

import com.project.shopapp.dtos.coupon.conditions.BaseCondition;
import com.project.shopapp.enums.AttributeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InCondition extends BaseCondition {

    private final String[] requiredValues;
    private final String[] attributeValues;

    public InCondition(String attribute, AttributeType attributeType, String value, Map<String, Object> attributeValueMap) {
        super(attribute, attributeType);
        this.requiredValues = value.split(";");
        this.attributeValues = (String[]) attributeValueMap.get(attribute);
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

}
