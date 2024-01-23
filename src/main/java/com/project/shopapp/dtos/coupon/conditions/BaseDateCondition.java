package com.project.shopapp.dtos.coupon.conditions;

import com.project.shopapp.enums.Attribute;
import com.project.shopapp.enums.AttributeType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class BaseDateCondition extends BaseCondition {
    protected final LocalDate fromDate;
    protected final LocalDate toDate;
    protected final LocalDate attributeValue;

    public BaseDateCondition(Attribute attribute, String value, Object attributeValue) {
        super(attribute);
        if (!attribute.getType().isValid(value, attributeValue)) {
            throw new IllegalArgumentException("Value is not valid");
        }
        this.attributeValue = (LocalDate) attributeValue;
        String[] rangeDateStr = value.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.fromDate = LocalDate.parse(rangeDateStr[0], formatter);
        this.toDate = LocalDate.parse(rangeDateStr[1], formatter);
    }

    @Override
    public AttributeType getRequiredAttributeType() {
        return AttributeType.DATE;
    }
}
