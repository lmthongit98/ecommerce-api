package com.project.shopapp.dtos.coupon.conditions;

import com.project.shopapp.enums.AttributeType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public abstract class BaseDateCondition extends BaseCondition {
    protected final LocalDate fromDate;
    protected final LocalDate toDate;
    protected final LocalDate attributeValue;

    public BaseDateCondition(String attribute, AttributeType attributeType, String value, Map<String, Object> attributeValueMap) {
        super(attribute, attributeType);
        Object actualValue = attributeValueMap.get(attribute);
        if (!AttributeType.DATE.equals(attributeType) || !attributeType.isValid(value, actualValue)) {
            throw new IllegalArgumentException("Type mismatch");
        }
        this.attributeValue = (LocalDate) actualValue;
        String[] rangeDateStr = value.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.fromDate = LocalDate.parse(rangeDateStr[0], formatter);
        this.toDate = LocalDate.parse(rangeDateStr[1], formatter);
    }

}
