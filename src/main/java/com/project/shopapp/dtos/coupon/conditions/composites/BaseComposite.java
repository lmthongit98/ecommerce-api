package com.project.shopapp.dtos.coupon.conditions.composites;

import com.project.shopapp.dtos.coupon.conditions.GenericCondition;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BaseComposite implements GenericCondition {

    private final List<GenericCondition> conditions = new ArrayList<>();

    public BaseComposite(List<GenericCondition> conditions) {
        conditions.forEach(this::add);
    }

    public void add(GenericCondition condition) {
        this.conditions.add(condition);
    }

    public void remove(GenericCondition condition) {
        this.conditions.remove(condition);
    }
}
