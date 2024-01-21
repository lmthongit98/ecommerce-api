package com.project.shopapp.dtos.coupon.conditions;

public interface GenericCondition {
    boolean isConditionMeet();
    String getInvalidMessage();
}
