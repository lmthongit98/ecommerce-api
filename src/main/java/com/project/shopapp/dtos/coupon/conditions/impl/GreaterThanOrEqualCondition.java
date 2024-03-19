package com.project.shopapp.dtos.coupon.conditions.impl;

import com.project.shopapp.dtos.coupon.conditions.BaseNumberCondition;
import com.project.shopapp.dtos.coupon.visitors.ConditionVisitor;
import com.project.shopapp.enums.Attribute;
import lombok.Getter;

@Getter
public class GreaterThanOrEqualCondition extends BaseNumberCondition {

    public GreaterThanOrEqualCondition(Attribute attribute, String value, Object attributeValue) {
        super(attribute, value, attributeValue);
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visit(this);
    }

}
