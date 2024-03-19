package com.project.shopapp.dtos.coupon.visitors;

import com.project.shopapp.dtos.coupon.conditions.impl.BetweenDateCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.GreaterThanCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.GreaterThanOrEqualCondition;
import com.project.shopapp.dtos.coupon.conditions.impl.InCondition;

public interface ConditionVisitor {
    void visit(BetweenDateCondition condition);
    void visit(GreaterThanCondition condition);
    void visit(GreaterThanOrEqualCondition condition);
    void visit(InCondition condition);
}
