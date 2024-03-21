package com.project.shopapp.dtos.coupon.conditions.composites;

import com.project.shopapp.dtos.coupon.conditions.GenericCondition;
import com.project.shopapp.dtos.coupon.visitors.ConditionVisitor;
import lombok.Getter;

import java.util.List;

@Getter
public class AndComposite extends BaseComposite {

    public AndComposite(List<GenericCondition> conditions) {
        super(conditions);
    }

    @Override
    public void accept(ConditionVisitor visitor) {
        visitor.visit(this);
    }

}
