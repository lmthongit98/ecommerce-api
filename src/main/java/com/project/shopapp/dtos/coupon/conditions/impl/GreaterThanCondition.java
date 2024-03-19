package com.project.shopapp.dtos.coupon.conditions.impl;

import com.project.shopapp.dtos.coupon.conditions.BaseNumberCondition;
import com.project.shopapp.dtos.coupon.visitors.ConditionVisitor;
import com.project.shopapp.enums.Attribute;

public class GreaterThanCondition extends BaseNumberCondition {

    public GreaterThanCondition(Attribute attribute, String value, Object attributeValue) {
        super(attribute, value, attributeValue);
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visit(this);
    }

}
