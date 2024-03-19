package com.project.shopapp.dtos.coupon.conditions;

import com.project.shopapp.dtos.coupon.visitors.ConditionVisitor;

public interface GenericCondition {
    void accept(ConditionVisitor visitor);
}
